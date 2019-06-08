package com.linkedintools.di.components

import com.linkedintools.di.modules.GroupsFragmentModule
import com.linkedintools.ui.main.fragments.groups.GroupsFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(GroupsFragmentModule::class)])
interface GroupsFragmentComponent {
    fun inject(groupsFragment: GroupsFragment)
}