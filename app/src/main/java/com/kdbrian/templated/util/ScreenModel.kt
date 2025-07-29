package com.kdbrian.templated.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias UiState<T> = MutableStateFlow<T>

abstract class ScreenModel<T> : ViewModel() {
    abstract val mutablestate: UiState<T>
    val state get() = mutablestate.asStateFlow()

}