package com.linkedintools

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import com.linkedintools.da.Repository
import com.linkedintools.di.components.BusComponent
import com.linkedintools.di.components.DaggerAppComponent
import com.linkedintools.di.components.DaggerBusComponent
import com.linkedintools.di.modules.BusModule
import com.linkedintools.service.ServiceUtil
import com.linkedintools.ui.utils.Notifications
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class App : @Inject Application() {

    @Inject
    lateinit var serviceUtil: ServiceUtil

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var notifications: Notifications

    //@Inject
    lateinit var mBusComponent: BusComponent

    private val mActivityLifecycleTracker: AppLifecycleTracker = AppLifecycleTracker()


    override fun onCreate() {
        super.onCreate()
        context = this

        mBusComponent = DaggerBusComponent.builder().busModule(BusModule).build()

        DaggerAppComponent
            .builder()
            .build()
            .inject(this)

        registerActivityLifecycleCallbacks(mActivityLifecycleTracker)

        // Global error handler for exceptions not caught in Rx.
        RxJavaPlugins.setErrorHandler { error ->
            notifications.displayErrorMessage(R.string.fatal_error)
        }
    }

    companion object {
        lateinit var context: App
        //private set
    }

    var bus: BusComponent
        get() = mBusComponent
        private set(value) {}


    fun displayErrorMessage(resId: Int) {
        notifications.displayErrorMessage(resId)
    }


    // Returns the current activity.
    var currentActivity: Activity?
        get() = mActivityLifecycleTracker.currentActivity
        private set(value) {}


    // Root view of the current activity.
    var currentActivityRootView: View?
        get() {
            if (mActivityLifecycleTracker.currentActivity == null)
                return null
            else {
                return mActivityLifecycleTracker.currentActivity!!.window.decorView.rootView
            }
        }
        set(value) {}


    /**
     * Callbacks for handling the lifecycle of activities.
     */
    class AppLifecycleTracker : Application.ActivityLifecycleCallbacks {

        private var mCurrentActivity: Activity? = null

        var currentActivity: Activity?
            get() = mCurrentActivity
            private set(value) {}

        override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
            mCurrentActivity = activity
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityStopped(activity: Activity?) {
            if ((mCurrentActivity != null) && (activity == mCurrentActivity))
                mCurrentActivity = null
        }


        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
        }
    }
}