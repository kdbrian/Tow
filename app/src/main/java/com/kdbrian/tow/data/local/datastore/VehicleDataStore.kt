package com.kdbrian.tow.data.local.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kdbrian.tow.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import java.util.Collections



private val Context.savedVehiclesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "vehicle_store"
)

private val VEHICLE_LIST_KEY = stringPreferencesKey("vehicle_list")


class VehicleDataStore(
    private val context: Context
) {

    fun loadVehicles(context: Context): Flow<List<Vehicle>> {
        return context.savedVehiclesDataStore.data.map { prefs ->
            val json = prefs[VEHICLE_LIST_KEY]
            try {
                json?.let { Json.decodeFromString(it) } ?: emptyList()
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    suspend fun saveVehicles(vehicles: List<Vehicle>) {
        val current = loadVehicles(context).first()
        val merged = (current + vehicles).distinctBy { it.model }
        val json = Json.encodeToString(merged)

        context.savedVehiclesDataStore.edit { it[VEHICLE_LIST_KEY] = json }
    }

    suspend fun addVehicle(vehicle: Vehicle) {
        saveVehicles(Collections.singleton(vehicle).toList())
    }

    suspend fun removeVehicleByModel(model: String) {
        val current = loadVehicles(context).first()
        val updated = current.filterNot { it.model == model }
        val json = Json.encodeToString(updated)

        context.savedVehiclesDataStore.edit { it[VEHICLE_LIST_KEY] = json }
    }

    suspend fun clearAll(context: Context) {
        context.savedVehiclesDataStore.edit { it.remove(VEHICLE_LIST_KEY) }
    }


}
