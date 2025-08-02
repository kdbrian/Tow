package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdbrian.tow.data.local.VehicleService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyVehiclesViewModel(
    private val vehicleService: VehicleService
) : ViewModel() {

    init {
        loadMyVehicles()
    }


    private val mutableState: MutableStateFlow<MyVehiclesUiState> =
        MutableStateFlow(MyVehiclesUiState())
    val uiState = mutableState.asStateFlow()

    fun loadMyVehicles() {
        viewModelScope.launch {
            val vehicles = vehicleService.loadSavedVehicles()
            vehicles.onSuccess {
                mutableState.value = mutableState.value.copy(
                    mine = it
                )
            }
        }
    }


}