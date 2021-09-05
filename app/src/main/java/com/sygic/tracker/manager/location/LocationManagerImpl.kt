package com.sygic.tracker.manager.location

import android.annotation.SuppressLint
import android.os.HandlerThread
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.sygic.tracker.model.location.GeoLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MissingPermission")
class LocationManagerImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationManager {

    override suspend fun getLastKnownPosition(): GeoLocation? {
        return fusedLocationProviderClient.lastLocation.await()?.let {
            GeoLocation(it.latitude, it.longitude)
        }
    }

    override suspend fun getPositions(): Flow<GeoLocation> {
        return callbackFlow {

            val callback = object : LocationCallback() {
                override fun onLocationResult(location: LocationResult) {
                    super.onLocationResult(location)
                    launch { send(GeoLocation(location.lastLocation.latitude, location.lastLocation.longitude)) }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.create().apply {
                    interval = 1000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                callback,
                HandlerThread("LocationHandler").looper
            )

            awaitClose { fusedLocationProviderClient.removeLocationUpdates(callback) }
        }
    }

}