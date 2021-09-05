package com.sygic.tracker.manager.location

import com.sygic.tracker.model.location.GeoLocation
import kotlinx.coroutines.flow.Flow

interface LocationManager {

    suspend fun getLastKnownPosition(): GeoLocation?

    suspend fun getPositions(): Flow<GeoLocation>

}
