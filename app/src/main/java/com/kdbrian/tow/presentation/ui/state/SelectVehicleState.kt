package com.kdbrian.tow.presentation.ui.state

import androidx.compose.foundation.text.input.TextFieldState
import com.kdbrian.tow.domain.model.Vehicle
import kotlinx.serialization.Serializable
import org.koin.core.logger.MESSAGE

//@Serializable
data class SelectVehicleState(
    val message: String? = null,
    val selected: Vehicle? = null,
    val models: List<Vehicle>? = null,
    val query: TextFieldState = TextFieldState()
)