package com.kdbrian.templated.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class to represent a typical vehicle, including fields for both ICE and EV.
 *
 * @param plateNumber The vehicle's license plate number.
 * @param model The make and model of the vehicle.
 * @param mileage The current mileage of the vehicle in kilometers or miles.
 * @param fuelTankCapacity The fuel tank capacity in liters (for ICE vehicles). Nullable for EVs.
 * @param batteryCapacityKWh The battery capacity in kWh (for EV vehicles). Nullable for ICE vehicles.
 */
@Serializable
data class Vehicle(
    val plateNumber: String,
    val model: String,
    val mileage: Int,
    val fuelTankCapacity: Double? = null,
    val batteryCapacityKWh: Double? = null,
    val lastServiced: Long? = null,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    companion object {
        val dreamCar = Vehicle(
            model = "Koinsegg",
            plateNumber = "ERT 321",
            mileage = 800,
        )

    }
}
