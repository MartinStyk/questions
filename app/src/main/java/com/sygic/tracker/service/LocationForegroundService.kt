package com.sygic.tracker.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sygic.tracker.R
import timber.log.Timber

private const val CHANNEL_ID = "notifChannel"
private const val NOTIF_ID = 987

class LocationForegroundService : Service() {

    inner class Binder: android.os.Binder() {
        fun startForeground() {
            Timber.tag("LocationService").i("startForeground()")
            this@LocationForegroundService.startForeground()
        }
        fun stopForeground() {
            Timber.tag("LocationService").i("stopForeground()")
            stopForeground(true)
        }
    }

    private val binder = Binder()

    override fun onCreate() {
        super.onCreate()
        startForeground()
        stopForeground(true)
    }

    private fun startForeground() = startForeground(NOTIF_ID, buildNotification())

    private fun buildNotification() : Notification {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_NONE)
            notificationManager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Sygic Tracker")
            .setContentText("Running on background...")
            .build()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

}