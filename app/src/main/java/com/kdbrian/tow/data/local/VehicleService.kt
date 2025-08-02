package com.kdbrian.tow.data.local

import com.kdbrian.tow.BuildConfig
import com.kdbrian.tow.domain.model.Vehicle

interface VehicleService {

    val collection: String
        get() = "${BuildConfig.APPLICATION_ID}:vehicles"

    suspend fun loadModels(): Result<List<Vehicle>>
    suspend fun saveVehicle(vehicle: Vehicle): Result<String>
    suspend fun loadSavedVehicles(): Result<List<Vehicle>>
}