package com.sygic.tracker

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.sygic.tracker.service.LocationServiceHandler
import com.sygic.tracker.util.LogUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class TrackingApp : Application() {

    @Inject
    lateinit var locationServiceHandler: LocationServiceHandler

    override fun onCreate() {
        super.onCreate()
        Timber.plant(*LogUtils.logTrees())
        ProcessLifecycleOwner.get().lifecycle.addObserver(locationServiceHandler)
    }
}