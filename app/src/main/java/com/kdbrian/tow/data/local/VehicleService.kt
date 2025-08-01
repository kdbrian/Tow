package com.kdbrian.tow.data.local

import com.kdbrian.tow.domain.model.Vehicle

interface VehicleService {
    suspend fun loadModels() : Result<List<Vehicle>>
}