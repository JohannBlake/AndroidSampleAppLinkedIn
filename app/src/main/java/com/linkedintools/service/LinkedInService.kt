package com.linkedintools.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.di.components.DaggerServiceDependenciesComponent
import com.linkedintools.rx.DefaultJitter
import com.linkedintools.rx.ExpBackoff
import com.linkedintools.ui.utils.Notifications
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * This service handles retrieving data from LinkedIn and sending messages to connections.
 * This services should never run all the time. It should terminate after completing all requests
 * requested by the calling client.
 */
class LinkedInService @Inject constructor() : Service() {

    @Inject
    lateinit var notifications: Notifications

    init {
        DaggerServiceDependenciesComponent
            .builder()
            .build()
            .inject(this)
    }

    private lateinit var mContext: Context
    private val binder = LocalBinder()
    private val mDisposables: CompositeDisposable = CompositeDisposable()
    var retrievingConnections: Boolean = false

    override fun onCreate() {
        super.onCreate()

        App.context.serviceUtil.serviceIsRunning = true

        mContext = this
        startForeground(notifications.NOTIFICATION_ID_LINKEDIN_SERVICE, notifications.createServiceNotification())
    }


    fun terminateService() {
        App.context.serviceUtil.serviceIsRunning = false
        stopForeground(true)
        stopSelf()
    }


    override fun onDestroy() {
        super.onDestroy()
        mDisposables.clear()
    }


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LinkedInService = this@LinkedInService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private lateinit var connectionsDisposable: Disposable


    @SuppressLint("CheckResult")
    fun getLinkedInConnections() {

        if (retrievingConnections)
            return

        retrievingConnections = true

        App.context.bus.onConnectionsRetrievalStarted().onNext(Unit)

        var startPos = 0
        val startPositions = BehaviorSubject.createDefault(startPos)

        startPositions.flatMap { startPos -> App.context.repository.getLinkedInConnections(startPos) }
            .flatMap { connections ->
                App.context.repository.storeConnections(connections)
            }
            .doOnNext { connections ->
                run {
                    if (connections.isNotEmpty()) {
                        startPos += 40
                        startPositions.onNext(startPos)
                    }
                }
            }
            .retryWhen(ExpBackoff(DefaultJitter(), delay = 1, unit = TimeUnit.SECONDS, retries = 5)) // About 30 seconds max.
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { connections ->
                    if (connections.isEmpty()) {
                        getLinkedInConnectionsCompleted()
                    }
                },
                { ex ->
                    App.context.notifications.displayErrorMessage(R.string.problem_retrieving_connections)
                    getLinkedInConnectionsCompleted()
                }
            )
    }


    private fun getLinkedInConnectionsCompleted() {
        App.context.bus.onConnectionsRetrievalCompleted().onNext(Unit)
        retrievingConnections = false;
        terminateService()
    }
}