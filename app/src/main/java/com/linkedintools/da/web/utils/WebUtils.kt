package com.linkedintools.da.web.utils

import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.linkedintools.App

/**
 * Utility functions when working with web-based data.
 */
class WebUtils {
    companion object {
        /**
         * Deletes all the cookies cached for the app.
         */
        fun clearCookies() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                CookieManager.getInstance().removeAllCookies(null)
                CookieManager.getInstance().flush()
            } else {
                val cookieSyncMngr = CookieSyncManager.createInstance(App.context)
                cookieSyncMngr.startSync()
                val cookieManager = CookieManager.getInstance()
                cookieManager.removeAllCookie()
                cookieManager.removeSessionCookie()
                cookieSyncMngr.stopSync()
                cookieSyncMngr.sync()
            }
        }
    }
}