package com.kdbrian.templated.domain.model

/**
 * Data class to represent a typical vehicle, including fields for both ICE and EV.
 *
 * @param plateNumber The vehicle's license plate number.
 * @param model The make and model of the vehicle.
 * @param mileage The current mileage of the vehicle in kilometers or miles.
 * @param fuelTankCapacity The fuel tank capacity in liters (for ICE vehicles). Nullable for EVs.
 * @param batteryCapacityKWh The battery capacity in kWh (for EV vehicles). Nullable for ICE vehicles.
 */
data class Vehicle(
    val plateNumber: String,
    val model: String,
    val mileage: Int, // Assuming mileage is an integer
    val fuelTankCapacity: Double? = null, // Nullable for EVs
    val batteryCapacityKWh: Double? = null // Nullable for ICE vehicles
)
