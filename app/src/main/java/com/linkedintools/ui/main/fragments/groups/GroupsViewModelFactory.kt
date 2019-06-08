package com.linkedintools.ui.main.fragments.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedintools.bo.GroupsBO

class GroupsViewModelFactory(
    private val groupsBO: GroupsBO
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GroupsViewModel(groupsBO) as T
    }
}