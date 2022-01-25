package com.example.interview.persistance

import androidx.room.Query

interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    suspend fun getAll(): List<VehicleEntity>
}