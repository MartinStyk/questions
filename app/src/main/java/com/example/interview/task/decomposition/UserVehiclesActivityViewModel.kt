package com.example.interview.task.decomposition

import androidx.lifecycle.*
import com.example.interview.api.UserApi
import com.example.interview.persistance.VehicleDatabase
import com.example.interview.persistance.VehicleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel handles network API calls, and database access.
 * Is it possible to decompose the code more efficiently?
 */
@HiltViewModel
class UserVehiclesActivityViewModel @Inject constructor(
    private val userApi: UserApi,
    private val vehicleDatabase: VehicleDatabase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> = _userName

    private val _vehicles = MutableLiveData<List<VehicleEntity>>()
    val vehicles : LiveData<List<VehicleEntity>> = _vehicles

    init {
        viewModelScope.launch {
            _userName.value = userApi.getUserProfile().name
        }
        viewModelScope.launch {
            _vehicles.value = vehicleDatabase.vehicleDao().getAll()
        }
    }

}