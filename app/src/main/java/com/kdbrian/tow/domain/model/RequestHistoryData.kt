package com.kdbrian.tow.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class to represent a single request history item.
 *
 * @param id Unique identifier for the request.
 * @param serviceName The name of the service (e.g., "Toll Service").
 * @param dateTimestamp The timestamp of the request in milliseconds.
 * @param totalCost The total cost of the request.
 * @param currency The currency symbol (e.g., "Ksh").
 * @param vehicleNumber The vehicle's registration number (e.g., "VXY123R").
 * @param providerId The ID of the service provider.
 * @param vehicleId The ID of the vehicle associated with the request.
 */
@Serializable
data class RequestHistoryData(
    val id: String,
    val serviceName: String,
    val dateTimestamp: Long,
    val totalCost: Double,
    val currency: String,
    val vehicleNumber: String,
    val providerId: String, // Added as per your request
    val vehicleId: String // Added as per your request
){
    companion object {
        val demos= listOf(
            RequestHistoryData(
                id = "1",
                serviceName = "Toll Service",
                dateTimestamp = 1624556800000,
                totalCost = 100.0,
                currency = "Ksh",
                vehicleNumber = "VXY123R",
                providerId = "1",
                vehicleId = "1"
            ),
            RequestHistoryData(
                id = "2",
                serviceName = "Toll Service",
                dateTimestamp = 1624556800000,
                totalCost = 100.0,
                currency = "Ksh",
                vehicleNumber = "VXY123R",
                providerId = "1",
                vehicleId = "1"
            )
        )
    }
}