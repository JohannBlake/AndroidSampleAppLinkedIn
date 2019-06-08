package com.linkedintools.ui.main.fragments.connections

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.bo.ConnectionsBO
import com.linkedintools.da.livedata.MutableLiveDataEx
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.ui.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val PAGE_SIZE = 40

class ConnectionsViewModel @Inject constructor(private val connectionsBO: ConnectionsBO) : BaseViewModel() {

    val onConnectionsCountRetrieved: MutableLiveData<Int> = MutableLiveData()
    val onConnectionsRetrievalStarted: MutableLiveDataEx<Unit> = MutableLiveDataEx()
    val onConnectionsRetrievalCompleted: MutableLiveDataEx<Unit> = MutableLiveDataEx()

    val connectionsByFirstName: LiveData<PagedList<LinkedInConnection>> =
        LivePagedListBuilder(
            connectionsBO.getConnectonsByFirstName(), PAGE_SIZE
        ).build()

    init {
        disposables.add(App.context.bus.onConnectionsRetrievalStarted().subscribe {
            onConnectionsRetrievalStarted.notifyWithoutData()
        })

        disposables.add(App.context.bus.onConnectionsRetrievalCompleted().subscribe {
            onConnectionsRetrievalCompleted.notifyWithoutData()
        })
    }

    fun getConnectionsIfNoneExist() {
        connectionsBO.getConnectionsIfNoneExist()
    }

    fun getConnections() {
        connectionsBO.getConnections()
    }

    fun notifyIfRetrievingConnections() {
        if (connectionsBO.isRetrievingConnectitons)
            onConnectionsRetrievalStarted.notifyWithoutData()
    }

    @SuppressLint("CheckResult")
    fun getConnectionsCount() {
        val disposable = connectionsBO.getConnectionsCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { count -> onConnectionsCountRetrieved.postValue(count) },
                { ex -> App.context.displayErrorMessage(R.string.problem_retrieving_total_connection_count) }
            )

        disposables.add(disposable)
    }
}