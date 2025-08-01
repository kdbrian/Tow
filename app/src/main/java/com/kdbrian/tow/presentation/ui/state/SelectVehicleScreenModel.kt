package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.viewModelScope
import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.presentation.ui.screens.SelectVehicle
import com.kdbrian.tow.util.ScreenModel
import com.kdbrian.tow.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SelectVehicleScreenModel(
    private val vehicleService: VehicleService
) : ScreenModel<SelectVehicleState>() {

    override val mutablestate: UiState<SelectVehicleState>
        get() = MutableStateFlow(SelectVehicleState())


    fun loadModels() {
        viewModelScope.launch {
            val models = vehicleService.loadModels()

            models.onFailure {
                mutablestate.emit(
                    mutablestate.value.copy(message = it.message)
                )
            }

            models.onSuccess {
                mutablestate.emit(
                    mutablestate.value.copy(models = it, message = "Retrieved ${it.size} models")
                )
            }
        }
    }

    fun setSelected(vehicle: Vehicle) {
        viewModelScope.launch {
            mutablestate.emit(
                mutablestate.value.copy(selected = vehicle)
            )
        }
    }


}