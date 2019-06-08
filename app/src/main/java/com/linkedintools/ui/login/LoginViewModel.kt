package com.linkedintools.ui.login

import android.annotation.SuppressLint
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import androidx.lifecycle.MutableLiveData
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.da.livedata.MutableLiveDataEx
import com.linkedintools.da.web.urls.LINKEDIN_FEED_PAGE
import com.linkedintools.da.web.urls.LINKEDIN_HOME_PAGE
import com.linkedintools.da.web.utils.WebUtils
import com.linkedintools.ui.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel : BaseViewModel() {

    val onAuthenticateUser: MutableLiveDataEx<Unit> = MutableLiveDataEx()
    val onLoginSuccess: MutableLiveDataEx<Unit> = MutableLiveDataEx()
    val onHideLinkedInLoginWhileProcessing: MutableLiveDataEx<Unit> = MutableLiveDataEx()
    val onLoginNotNeeded: MutableLiveDataEx<Unit> = MutableLiveDataEx()
    val onError: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
    }


    fun login() {
        if (App.context.repository.cookie == null) {
            WebUtils.clearCookies()
            CookieSyncManager.getInstance().sync();
            onAuthenticateUser.notifyWithoutData()
        } else {
            onLoginNotNeeded.notifyWithoutData()
        }
    }

    fun shouldOverrideUrlLoading(url: String?) {
        if (url!!.startsWith(LINKEDIN_FEED_PAGE)) {
            onHideLinkedInLoginWhileProcessing.notifyWithoutData()
        }
    }

    @SuppressLint("CheckResult")
    fun onPageFinished(url: String?) {
        CookieSyncManager.getInstance().sync()

        if (url!!.equals(LINKEDIN_HOME_PAGE)) {
            val cookies = CookieManager.getInstance().getCookie(url)
            App.context.repository.cookie = cookies

            // Extract the csrf token.
            val cookieVals = cookies.split(";")

            for (keyPairVals in cookieVals) {
                val keyPair = keyPairVals.split("=")

                if (keyPair[0].equals("JSESSIONID")) {
                    App.context.repository.csrfToken = keyPair[1].replace("\"", "")
                    break;
                }
            }

            // TODO: Add retryWhen using an exponential backoff that is designed to work with a Single. The ExpBackOff class is designed
            // to work with Observables.
            disposables.add(App.context.repository.getLinkedInUserSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { userSettings ->
                        App.context.repository.cachedLinkedInUserSettings = userSettings
                        onLoginSuccess.notifyWithoutData()
                    },
                    { error -> App.context.displayErrorMessage(R.string.problem_retrieving_linkedin_setttings) }
                ))
        }
    }
}
