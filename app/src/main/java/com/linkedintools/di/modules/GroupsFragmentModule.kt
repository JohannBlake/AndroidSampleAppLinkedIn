package com.linkedintools.di.modules

import com.linkedintools.bo.GroupsBO
import com.linkedintools.ui.main.fragments.groups.GroupsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class GroupsFragmentModule {
    @Provides
    fun providesViewModelFactory(groupsBO: GroupsBO): GroupsViewModelFactory {
        return GroupsViewModelFactory(groupsBO)
    }
}