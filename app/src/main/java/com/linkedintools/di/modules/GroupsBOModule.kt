package com.linkedintools.di.modules

import com.linkedintools.bo.GroupsBO
import dagger.Module
import dagger.Provides

@Module
object GroupsBOModule {
    @JvmStatic
    @Provides
    fun provideGroupsBO() : GroupsBO {
        return GroupsBO()
    }
}