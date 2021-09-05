package com.sygic.tracker.screen.tracking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.sygic.tracker.R
import com.sygic.tracker.manager.location.LocationRequestManager
import com.sygic.tracker.manager.permission.PermissionManager
import com.sygic.tracker.screen.map.MapDataModel
import com.sygic.tracker.util.SignalingLiveData
import com.sygic.tracker.util.TextInfo
import com.sygic.tracker.util.components.SnackBarComponent
import com.sygic.tracker.util.components.ToastComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


private const val MY_POSITION_ZOOM_LEVEL = 15f

class StoresFragmentViewModel @AssistedInject constructor(
    @Assisted private val mapDataModel: MapDataModel,
    private val locationRequestManager: LocationRequestManager,
    private val permissionManager: PermissionManager,
) : ViewModel() {

    private val showToastSignal = SignalingLiveData<ToastComponent>()
    val showToast: LiveData<ToastComponent> = showToastSignal

    private val showIndefiniteSnackbarLiveData = MutableLiveData<SnackBarComponent?>()
    val showIndefiniteSnackbar: LiveData<SnackBarComponent?> = showIndefiniteSnackbarLiveData

    init {
        viewModelScope.launch {
            ensureLocationReady()
        }
    }

    private suspend fun ensureLocationReady() {
        if (!locationRequestManager.isGpsEnabled) {
            val isGpsEnabled = locationRequestManager.requestGpsEnable()
            if (!isGpsEnabled) {
                showIndefiniteSnackbarLiveData.postValue(
                    SnackBarComponent(
                        text = TextInfo.from(R.string.allow_device_location_to_get_your_position),
                        action = TextInfo.from(R.string.allow),
                        callback = { viewModelScope.launch { ensureLocationReady() } },
                        length = Snackbar.LENGTH_INDEFINITE
                    )
                )
                return
            }
        }
        if (!permissionManager.hasPermissionGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            val hasPermission =
                permissionManager.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)).grantedPermissions.isNotEmpty()
            if (!hasPermission) {
                showIndefiniteSnackbarLiveData.postValue(
                    SnackBarComponent(
                        text = TextInfo.from(R.string.allow_acces_to_your_location),
                        action = TextInfo.from(R.string.allow),
                        callback = { viewModelScope.launch { ensureLocationReady() } },
                        length = Snackbar.LENGTH_INDEFINITE
                    )
                )
                return
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(mapDataModel: MapDataModel): StoresFragmentViewModel
    }

}