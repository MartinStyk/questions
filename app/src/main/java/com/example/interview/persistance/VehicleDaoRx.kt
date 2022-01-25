package com.example.interview.persistance

import androidx.room.Query
import io.reactivex.Single

interface VehicleDaoRx {
    @Query("SELECT * FROM vehicles")
    fun getAll(): Single<List<VehicleEntity>>
}