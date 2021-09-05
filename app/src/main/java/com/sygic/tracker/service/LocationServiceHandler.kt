package com.sygic.tracker.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.sygic.tracker.dependencyinjection.util.ForApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationServiceHandler @Inject constructor(
    @ForApplication private val context: Context,
) : DefaultLifecycleObserver {

    private var serviceBinder: LocationForegroundService.Binder? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceBinder = service as LocationForegroundService.Binder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBinder = null
        }
    }

    init {
        startService()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        serviceBinder?.stopForeground()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        serviceBinder?.startForeground()
    }

    private fun startService() {
        val intent = Intent(context, LocationForegroundService::class.java)
        intent.setPackage(context.packageName)
        ContextCompat.startForegroundService(context, intent)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

}