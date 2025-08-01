package com.kdbrian.tow.data.local.impl

import android.content.Context

class VehicleRepoImpl(
//    private val moshi: Moshi,
    private val context: Context
)
//    : VehicleRepo() {
//    override suspend fun loadModels(): Result<List<Vehicle>> = withContext(Dispatchers.IO) {
//        try {
//            val type = Types.newParameterizedType(List::class.java, Vehicle::class.java)
//            val adapter: JsonAdapter<List<Vehicle>> = moshi.adapter(type)
//
//            context.assets.open("models.json").use { inputStream ->
//                JsonReader.of(inputStream.source().buffer()).use { reader ->
//                    val vehicles = adapter.fromJson(reader)
//                    vehicles?.let {
//                        Timber.d("vehicles = ${it.size}")
//                        Result.success(vehicles)
//                    } ?: run {
//                        Timber.d("failure")
//                        Result.failure(Exception("Failed to parse JSON"))
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            Timber.d("failure = ${e.message}")
//            Result.failure(e)
//        }
//    }
//}