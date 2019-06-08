package com.linkedintools.ui.main.fragments.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.linkedintools.bo.TipsBO

class TipsViewModelFactory(
    private val tipsBO: TipsBO
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TipsViewModel(tipsBO) as T
    }
}