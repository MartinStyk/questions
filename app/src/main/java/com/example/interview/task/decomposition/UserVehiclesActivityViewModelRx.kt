package com.example.interview.task.decomposition

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interview.api.UserApiRx
import com.example.interview.persistance.VehicleDatabase
import com.example.interview.persistance.VehicleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * ViewModel handles network API calls, and database access.
 * Is it possible to decompose the code more efficiently?
 */
@HiltViewModel
class UserVehiclesActivityViewModelRx @Inject constructor(
    private val userApi: UserApiRx,
    private val vehicleDatabase: VehicleDatabase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _vehicles = MutableLiveData<List<VehicleEntity>>()
    val vehicles: LiveData<List<VehicleEntity>> = _vehicles

    private val disposables = CompositeDisposable()

    init {
        disposables.add(
            userApi.getUserProfile().subscribe({ userProfile ->
                _userName.value = userProfile.name
            }, { throwable ->
                Log.e("UserVehiclesActivity", "Error loading user profile", throwable)
            })
        )

        disposables.add(
            vehicleDatabase.vehicleDaoRx().getAll().subscribe({ vehicleList ->
                _vehicles.value = vehicleList
            }, { throwable ->
                Log.e("UserVehiclesActivity", "Error loading vehicles", throwable)
            })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}