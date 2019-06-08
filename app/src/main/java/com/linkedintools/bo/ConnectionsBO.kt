package com.linkedintools.bo

import com.linkedintools.App
import com.linkedintools.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Handles the business logic for connections.
 */
class ConnectionsBO @Inject constructor() : BaseBusinessObject() {

    fun getConnectonsByFirstName() = App.context.repository.getConnectionsByFirstName()
    fun getConnectionsCount() = App.context.repository.getConnectionsCount()

    fun getConnectionsIfNoneExist() {
        disposables.add(App.context.repository.getConnectionsCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { count ->
                    if (count == 0) {
                        App.context.repository.getConnections()
                    }
                },
                { error -> App.context.displayErrorMessage(R.string.problem_retrieving_total_connection_count) }
            ))
    }

    fun getConnections() {
        App.context.repository.getConnections()
    }

    var isRetrievingConnectitons: Boolean
        get() = App.context.repository.isRetrievingConnections
        private set(value) {}


    override fun onDestroy() {
        super.onDestroy()
    }
}