package com.sygic.tracker.model.map

import com.sygic.tracker.model.location.GeoLocation

data class MapLocationZoomData(val location: GeoLocation, val zoom: Float? = null)