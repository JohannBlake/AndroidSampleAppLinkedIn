package com.linkedintools.di.components

import com.linkedintools.di.modules.MessagesFragmentModule
import com.linkedintools.ui.main.fragments.messages.MessagesFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(MessagesFragmentModule::class)])
interface MessagesFragmentComponent {
    fun inject(messagesFragment: MessagesFragment)
}