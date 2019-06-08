package com.linkedintools.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.PublishSubject
import javax.inject.Named
import javax.inject.Singleton


/**
 * This module provides the equivalent functionality of an event bus where you can publish and subscribe to events.
 */
@Module
object BusModule {
    const val EVENT_SERVICE_STARTED = "EVENT_SERVICE_STARTED"
    const val EVENT_CONNECTIONS_RETRIEVAL_STARTED = "EVENT_CONNECTIONS_RETRIEVAL_STARTED"
    const val EVENT_CONNECTIONS_RETRIEVAL_COMPLETED = "EVENT_CONNECTIONS_RETRIEVAL_COMPLETED"

    @Provides
    @Singleton
    @Named(EVENT_CONNECTIONS_RETRIEVAL_STARTED)
    fun provideOnConnectionsRetrievalStarted(): PublishSubject<Unit> {
        return PublishSubject.create();
    }

    @Provides
    @Singleton
    @Named(EVENT_CONNECTIONS_RETRIEVAL_COMPLETED)
    fun provideOnConnectionsRetrievalCompleted(): PublishSubject<Unit> {
        return PublishSubject.create();
    }

    @Provides
    @Singleton
    @Named(EVENT_SERVICE_STARTED)
    fun provideOnServiceStarted(): PublishSubject<Unit> {
        return PublishSubject.create();
    }
}

