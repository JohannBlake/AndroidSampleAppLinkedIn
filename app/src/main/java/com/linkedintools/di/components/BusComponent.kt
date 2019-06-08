package com.linkedintools.di.components

import com.linkedintools.di.modules.BusModule
import dagger.Component
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Used to provide an Rx type of event bus that allows unconnected parts of the app to subscribe to events that publishers can publish.
 */
@Singleton
@Component(modules = [(BusModule::class)])
interface BusComponent {
    @Named(BusModule.EVENT_CONNECTIONS_RETRIEVAL_STARTED)
    fun onConnectionsRetrievalStarted(): PublishSubject<Unit>

    @Named(BusModule.EVENT_CONNECTIONS_RETRIEVAL_COMPLETED)
    fun onConnectionsRetrievalCompleted(): PublishSubject<Unit>

    @Named(BusModule.EVENT_SERVICE_STARTED)
    fun onServiceStarted(): PublishSubject<Unit>
}