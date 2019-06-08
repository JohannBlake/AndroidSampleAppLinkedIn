package com.linkedintools.bo

import io.reactivex.disposables.CompositeDisposable

open class BaseBusinessObject {
    private val mDisposables: CompositeDisposable = CompositeDisposable()

    val disposables: CompositeDisposable
    get() = mDisposables

    open fun onDestroy() {
        mDisposables.clear()
    }
}