package com.linkedintools.di.modules

import com.linkedintools.bo.MessagesBO
import dagger.Module
import dagger.Provides

@Module
object MessagesBOModule {
    @JvmStatic
    @Provides
    fun provideMessagesBO() : MessagesBO {
        return MessagesBO()
    }
}