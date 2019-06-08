package com.linkedintools.bo

import com.linkedintools.App
import com.linkedintools.model.LinkedInUserSettings
import javax.inject.Inject

/**
 * Handles stuff related to a user's session when they start the app.
 */
class SessionBO @Inject constructor() : BaseBusinessObject() {

    fun getLinkedInUserSettings(): LinkedInUserSettings {
        return App.context.repository.cachedLinkedInUserSettings!!
    }

    var firstUsageInitialized: Boolean
        get() = App.context.repository.firstUsageInitialized
        set(value) {
            App.context.repository.firstUsageInitialized = value
        }

    fun signOut() {
        App.context.repository.cookie = null
        App.context.repository.csrfToken = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}