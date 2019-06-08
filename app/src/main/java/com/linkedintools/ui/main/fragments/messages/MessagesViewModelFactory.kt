package com.linkedintools.ui.main.fragments.messages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedintools.bo.MessagesBO

class MessagesViewModelFactory(
    private val messagesBO: MessagesBO
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessagesViewModel(messagesBO) as T
    }
}