package com.kdbrian.tow.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

typealias UiState<T> = MutableStateFlow<T>

abstract class ScreenModel<T> : ViewModel() {
    abstract val mutablestate: UiState<T>
    val state get() = mutablestate
        .asStateFlow()


}