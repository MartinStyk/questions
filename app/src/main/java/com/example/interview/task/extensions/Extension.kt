package com.example.interview.task.extensions

import android.location.Location
import kotlinx.coroutines.flow.Flow
import okhttp3.Route
import javax.inject.Inject

data class BatteryProfile(val capacity: Float)
data class RouteRequest(val start: Location, val finish: Location, val waypoints: List<Location>)
interface Router {
    fun computeRoute(waypoints: List<Location>) : Flow<Result<Route>>
    fun computeRouteElectricRoute(waypoints: List<Location>, batteryProfile: BatteryProfile): Flow<Result<Route>>
}

interface ElectricVehicleSettings {
    fun isVehicleElectric() : Boolean
    fun currentBatteryProfile(): BatteryProfile
}

/**
 * Compare Extensions vs Repository design patterns.
 * What are pros/cons of each of them?
 */
/**
 * SOLUTION 1
 * Extension on RouteRequest
 */
fun RouteRequest.computeRoute(electricVehicleSettings: ElectricVehicleSettings, router: Router): Flow<Result<Route>> {
    val waypointsMerged = buildList { add(start); addAll(waypoints) ;add(finish) }
    return if (electricVehicleSettings.isVehicleElectric()) {
        router.computeRouteElectricRoute(waypointsMerged, electricVehicleSettings.currentBatteryProfile())
    } else {
        router.computeRoute(waypointsMerged)
    }
}

/**
 * SOLUTION 2
 * Repository
 */
class RoutingRepository @Inject constructor(
    private val electricVehicleSettings: ElectricVehicleSettings,
    private val router: Router,
) {
    fun computeRoute(routeRequest: RouteRequest): Flow<Result<Route>> {
        val waypointsMerged = buildList { add(routeRequest.start); addAll(routeRequest.waypoints);add(routeRequest.finish) }
        return if (electricVehicleSettings.isVehicleElectric()) {
            router.computeRouteElectricRoute(waypointsMerged, electricVehicleSettings.currentBatteryProfile())
        } else {
            router.computeRoute(waypointsMerged)
        }
    }
}