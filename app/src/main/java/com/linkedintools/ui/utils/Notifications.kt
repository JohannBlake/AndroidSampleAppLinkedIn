package com.linkedintools.ui.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.ui.main.MainActivity

/**
 * Provides utility functions to display various kinds of messages: Snackbar, AlertDialog, Statusbar notifications, etc.
 */
class Notifications {

    val CHANNEL_ID_LINKEDIN_SERVICE = "channelIdLinkedInService"
    val CHANNEL_NAME_LINKEDIN_SERVICE = App.context.getString(R.string.notification_category_name_service_status)


    // Notification IDs
    val NOTIFICATION_ID_LINKEDIN_SERVICE = 12345


    fun createServiceNotification(): Notification {
        return createNotification(
            R.string.linkedintools_service_running,
            R.string.click_here_to_open_linkedintools,
            R.drawable.ic_group,
            CHANNEL_ID_LINKEDIN_SERVICE,
            CHANNEL_NAME_LINKEDIN_SERVICE
        )
    }


    fun displayErrorMessage(resId: Int) {
        if (App.context.currentActivity == null) {
            showNotification(
                R.string.application_error,
                resId,
                R.drawable.ic_warning,
                CHANNEL_ID_LINKEDIN_SERVICE,
                CHANNEL_NAME_LINKEDIN_SERVICE,
                (0..0x7fffffff).random()
            )
        } else {
            showErrorSnackbar(resId)
        }
    }

    /**
     * Displays a message, either in an AlertDialog, if an activity is showning, or as a notification if no activity is showing.
     */
    fun displayMessage(resId: Int) {
        if (App.context.currentActivity == null) {
            createNotification(
                R.string.information,
                resId,
                R.drawable.ic_info,
                CHANNEL_ID_LINKEDIN_SERVICE,
                CHANNEL_NAME_LINKEDIN_SERVICE
            )
        } else {
            displayModalDialog(resId)
        }
    }

    private fun displayModalDialog(resId: Int) {
        val context = App.context.currentActivity as Context

        //val dialogBuilder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.CustomDialog))
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(context.getString(resId))
            .setCancelable(true)
            .setPositiveButton(App.context.getString(R.string.ok), DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })

        val alert = dialogBuilder.create()
        alert.setTitle(context.getString(R.string.app_name))
        alert.show()
    }


    /**
     * Displays a snackbar with normal text. Use showErrorSnackbar if you want to show an error message instead.
     * @param message The message to display
     * @param duration Set to Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_LONG or Snackbar.LENGTH_SHORT
     */
    fun showInfoSnackbar(resId: Int, duration: Int) {
        showSnackbar(resId, duration, false)
    }

    fun showErrorSnackbar(resId: Int) {
        showSnackbar(resId, Snackbar.LENGTH_INDEFINITE, true)
    }

    private fun showSnackbar(resId: Int, duration: Int, isErrorMessage: Boolean) {

        if (App.context.currentActivityRootView == null)
            return

        val view = App.context.currentActivityRootView!!.findViewById<CoordinatorLayout>(R.id.coordinator_layout_main)

        val snackbar = Snackbar.make(view, App.context.getString(resId), duration)

        if (duration == Snackbar.LENGTH_INDEFINITE) {
            snackbar.setAction(App.context.getString(R.string.cancel), View.OnClickListener {
                var x = 0
                x++
            })
        }

        if (isErrorMessage) {
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
            textView.setTextColor(App.context.resources.getColor(R.color.red_A200))
        }

        snackbar.show()
    }

    fun createNotification(titleResId: Int, textResId: Int, iconResId: Int, channeld: String, channelName: String): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(channeld, channelName)
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val pendingIntent: PendingIntent = Intent(App.context, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(App.context, 0, notificationIntent, 0)
        }

        val notification: Notification = NotificationCompat.Builder(App.context, channelId)
            .setContentTitle(App.context.getString(titleResId))
            .setContentText(App.context.getString(textResId))
            .setSmallIcon(iconResId)
            .setContentIntent(pendingIntent)
            .build()

        return notification
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = App.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }


    fun showNotification(titleResId: Int, textResId: Int, iconResId: Int, channeld: String, channelName: String, notificationId: Int) {
        with(NotificationManagerCompat.from(App.context)) {
            val notification = createNotification(titleResId, textResId, iconResId, channeld, channelName)
            notify(notificationId, notification)
        }
    }
}