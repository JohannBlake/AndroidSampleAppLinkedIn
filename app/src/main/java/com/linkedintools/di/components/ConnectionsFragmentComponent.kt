package com.linkedintools.di.components

import com.linkedintools.di.modules.ConnectionsFragmentModule
import com.linkedintools.ui.main.fragments.connections.ConnectionsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ConnectionsFragmentModule::class)])
interface ConnectionsFragmentComponent {
    fun inject(connectionsFragment: ConnectionsFragment)
}