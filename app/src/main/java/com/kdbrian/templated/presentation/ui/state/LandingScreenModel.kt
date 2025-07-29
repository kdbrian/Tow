package com.kdbrian.templated.presentation.ui.state

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.templated.util.ScreenModel
import com.kdbrian.templated.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LandingScreenModel(
    private val firebaseAuth: FirebaseAuth
) : ScreenModel<LandingUiState>() {

    override val mutablestate: UiState<LandingUiState>
        get() = MutableStateFlow(LandingUiState())

    init {
        viewModelScope.launch {
            mutablestate.emit(
                mutablestate.value.copy(
                    isAuthenticated = firebaseAuth.currentUser != null
                )
            )
        }
    }


}