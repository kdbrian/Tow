package com.kdbrian.tow.data.local.impl

import android.content.Context
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.domain.repo.VehicleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher

class VehicleRepoImpl(
    private val json: Json,
    private val context: Context
) : VehicleRepo() {
    override suspend fun loadModels(): Result<List<Vehicle>> = withContext(Dispatchers.IO) {
        try {
            val string = context.assets.open("models.json").bufferedReader().use {
                it.readText()
            }
            val vehicles = json.decodeFromString<List<Vehicle>>(string)
            Result.success(vehicles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}