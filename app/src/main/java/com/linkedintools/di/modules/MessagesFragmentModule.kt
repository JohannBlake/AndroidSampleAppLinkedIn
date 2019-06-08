package com.linkedintools.di.modules

import com.linkedintools.bo.MessagesBO
import com.linkedintools.ui.main.fragments.messages.MessagesViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MessagesFragmentModule {
    @Provides
    fun providesViewModelFactory(messagesBO: MessagesBO): MessagesViewModelFactory {
        return MessagesViewModelFactory(messagesBO)
    }
}