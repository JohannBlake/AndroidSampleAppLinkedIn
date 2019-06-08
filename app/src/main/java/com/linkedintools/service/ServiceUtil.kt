package com.linkedintools.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.linkedintools.App
import javax.inject.Inject


/**
 * Utility class that provides access to the mService.
 */
class ServiceUtil @Inject constructor() {

    var mService: LinkedInService? = null
    private var mBound: Boolean = false


    var service: LinkedInService?
        get() = mService
        private set(value) {}

    /** Defines callbacks for service binding, passed to bindService()  */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, mService: IBinder) {
            if (mBound) {
                val binder = mService as LinkedInService.LocalBinder
                this@ServiceUtil.mService = binder.getService()
                App.context.bus.onServiceStarted().onNext(Unit)
            }
        }

        /**
         * Useless callback. It only gets called if the service crashes or is killed off by the OS.
         */
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    /**
     * Starts the service.
     */
    fun startService() {
        Intent(App.context, LinkedInService::class.java).also { intent ->
            //App.context.unbindService(mConnection)
            App.context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    var serviceIsRunning: Boolean
        get() = mBound
        set(value) {
            mBound = value

            if (!mBound) {
                try {
                    App.context.unbindService(mConnection)
                } catch (ex: Exception) {
                }
            }
        }
}