package com.sygic.tracker.model.map

import androidx.annotation.Px
import com.sygic.tracker.model.location.GeoLocation

data class MapAreaZoomData(val locations: List<GeoLocation>, @Px val padding: Int)