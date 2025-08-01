package com.kdbrian.tow.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val vehicle: Vehicle,
    val plateNumber: String?=null,
    val isSaved: Boolean = false
)