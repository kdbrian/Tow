package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.presentation.ui.screens.SelectVehicle
import com.kdbrian.tow.util.Resource
import com.kdbrian.tow.util.ScreenModel
import com.kdbrian.tow.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import kotlin.time.Duration

class SelectVehicleScreenModel
