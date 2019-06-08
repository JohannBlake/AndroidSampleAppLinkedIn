package com.linkedintools.da.local

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.linkedintools.App
import com.linkedintools.model.LinkedInUserSettings

/**
 * Handles data stored in shared preferences.
 */
class SharedPrefs {

    val KEY_COOKIE = "cookie"
    val KEY_CSRF_TOKEN = "csrfToken"
    val KEY_FIRST_USAGE_INITIALIZED = "firstUsageInitialized"
    val KEY_LINKEDIN_USER_SETTINGS = "linkedInUserSettings"

    val mGson: Gson = Gson()

    fun putPref(key: String, value: Any?, type: Any) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(App.context)
        val editor = prefs.edit()

        if (type::class.isInstance(String)) {
            if (value == null)
                editor.putString(key, null)
            else
                editor.putString(key, value as String)
        } else {
            if (value == null)
                editor.putBoolean(key, false)
            else
                editor.putBoolean(key, value as Boolean)
        }

        editor.commit()
    }

    fun getPref(key: String, type: Any): Any? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(App.context)

        if (type::class.isInstance(String))
            return preferences.getString(key, null)
        else
            return preferences.getBoolean(key, false)
    }

    var firstUsageInitialized: Boolean
        get() = getPref(KEY_FIRST_USAGE_INITIALIZED, Boolean) as Boolean
        set(value) {
            putPref(KEY_FIRST_USAGE_INITIALIZED, value, Boolean)
        }

    var cookie: String?
        get() = getPref(KEY_COOKIE, String) as String?
        set(value) {
            putPref(KEY_COOKIE, value, String)
        }

    var csrfToken: String?
        get() = getPref(KEY_CSRF_TOKEN, String) as String?
        set(value) {
            putPref(KEY_CSRF_TOKEN, value, String)
        }

    var linkedInUserSettings: LinkedInUserSettings?
        get() {
            val info = getPref(KEY_LINKEDIN_USER_SETTINGS, String) as String?

            if (info != null) {
                return mGson.fromJson(info, LinkedInUserSettings::class.java)
            } else {
                return null
            }
        }
        set(value) {
            putPref(KEY_LINKEDIN_USER_SETTINGS, mGson.toJson(value), String)
        }
}