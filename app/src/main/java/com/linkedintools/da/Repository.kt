package com.linkedintools.da

import androidx.paging.DataSource
import com.linkedintools.App
import com.linkedintools.da.local.SharedPrefs
import com.linkedintools.da.local.room.EntityMapper
import com.linkedintools.da.local.room.RoomDao
import com.linkedintools.da.web.LinkedInAPI
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.model.LinkedInUserSettings
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Acts as a single interface to data throughout the app. However, calls to backend APIs should only be made by the service as it coordinates
 * multiple API calls and runs asynchronously to calls to the Room database.
 */
@Singleton
class Repository @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val linkedInAPI: LinkedInAPI,
    private val appDao: RoomDao,
    private val mapper: EntityMapper
) : IRepository {

    private var mGetConnections: Boolean = false
    private val mDisposables: CompositeDisposable = CompositeDisposable()

    init {
        mDisposables.add(App.context.bus.onServiceStarted().subscribe {
            if (mGetConnections) {
                mGetConnections = false
                App.context.serviceUtil.service?.getLinkedInConnections()
            }
        })
    }

    override var isRetrievingConnections: Boolean
        get() = App.context.serviceUtil.service?.retrievingConnections ?: false
        set(value) {}

    override var firstUsageInitialized: Boolean
        get() = sharedPrefs.firstUsageInitialized
        set(value) {
            sharedPrefs.firstUsageInitialized = value
        }

    // Cached Session cookie
    override var cookie: String?
        get() = sharedPrefs.cookie
        set(value) {
            sharedPrefs.cookie = value
        }

    // Cached csrf Token
    override var csrfToken: String?
        get() = sharedPrefs.csrfToken
        set(value) {
            sharedPrefs.csrfToken = value
        }

    // Cached LinkedIn user settings
    override var cachedLinkedInUserSettings: LinkedInUserSettings?
        get() = sharedPrefs.linkedInUserSettings
        set(value) {
            sharedPrefs.linkedInUserSettings = value
        }

    /**
     * Retrieves the user's LinkedIn settings from LinkedIn servers.
     */
    override fun getLinkedInUserSettings(): Single<LinkedInUserSettings> {
        return linkedInAPI.getSettings()
    }

    override fun getConnectionsByFirstName(): DataSource.Factory<Int, LinkedInConnection> {
        return appDao.getConnectionsByFirstName().map { mapper.fromDb(it) }
    }

    override fun getConnectionsCount(): Observable<Int> {
        return appDao.getConnectionsCount()
    }

    fun getConnections() {
        if (App.context.serviceUtil.serviceIsRunning) {
            App.context.serviceUtil.service?.getLinkedInConnections()
        } else {
            mGetConnections = true
            App.context.serviceUtil.startService()
        }
    }

    override fun getLinkedInConnections(startPos: Int): Observable<List<LinkedInConnection>> {
        return linkedInAPI.getConnections(startPos)
    }


    override fun storeConnections(connections: List<LinkedInConnection>): Observable<List<LinkedInConnection>> =
        Observable.fromCallable<List<LinkedInConnection>> {

            appDao.storeConnections(connections.map {
                mapper.toDb(it)
            })

            return@fromCallable connections
        }
}