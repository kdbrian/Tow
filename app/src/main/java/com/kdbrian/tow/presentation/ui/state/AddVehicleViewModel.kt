package com.kdbrian.tow.presentation.ui.state

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.type.LatLng
import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.util.getLocationName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddVehicleViewModel(
    private val vehicleService: VehicleService,
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {


    private val mutableUiState = MutableStateFlow(AddVehicleUiState())
    val uiState = mutableUiState.asStateFlow()


    fun setModel(model: String) = mutableUiState.value.model.setTextAndPlaceCursorAtEnd(model)
    fun setPlateNo(plateNo: String) =
        mutableUiState.value.plateNo.setTextAndPlaceCursorAtEnd(plateNo)

    fun setLocation(location: String) =
        mutableUiState.value.location.setTextAndPlaceCursorAtEnd(location)

    fun setType(type: String) = mutableUiState.value.type.setTextAndPlaceCursorAtEnd(type)


    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun loadMyLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
                it?.let {
                    val locationName = context.getLocationName(it.latitude, it.longitude)
                    mutableUiState.value.location.setTextAndPlaceCursorAtEnd(locationName.toString())
                }
            }
    }

    fun addVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleService.saveVehicle(vehicle)
            mutableUiState.value = AddVehicleUiState()
        }
    }

    fun setUseMyLocation(useMyLocation: Boolean) {
        mutableUiState.value = mutableUiState.value.copy(useMyLocation = useMyLocation)
    }

}