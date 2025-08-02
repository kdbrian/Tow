package com.kdbrian.tow.presentation.ui.state

import androidx.compose.foundation.text.input.TextFieldState

data class AddVehicleUiState(
    val useMyLocation: Boolean = false,
    val model: TextFieldState = TextFieldState(),
    val plateNo: TextFieldState = TextFieldState(),
    val type: TextFieldState = TextFieldState(),
    val location: TextFieldState = TextFieldState(),
)