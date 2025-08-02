package com.kdbrian.tow.presentation.ui.state

import com.kdbrian.tow.domain.model.Vehicle

data class MyVehiclesUiState(
    val mine: List<Vehicle>? = null
)