package com.linkedintools.ui.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel() : ViewModel() {
    protected var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }
}
