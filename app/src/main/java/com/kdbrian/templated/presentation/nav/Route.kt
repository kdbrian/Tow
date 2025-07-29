package com.kdbrian.templated.presentation.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class Route


@Serializable
data object LandingRoute

@Serializable
data object ProfileRoute

@Serializable
data object MyVehiclesRoute


@Serializable
data object AddVehicleRoute

@Serializable
data object EateriesRoute

@Serializable
data object RequestServiceRoute

@Serializable
data object ServicesRoute

@Serializable
data object RequestHistoryRoute


@Serializable
data class RequestDetailsRoute(val id: String)





