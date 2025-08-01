package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.tow.util.Resource
import com.kdbrian.tow.util.ScreenModel
import com.kdbrian.tow.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LandingScreenModel(
    private val firebaseAuth: FirebaseAuth
) : ScreenModel<LandingUiState>() {

    override val mutablestate: UiState<LandingUiState>
        get() = MutableStateFlow(Resource.Nothing())

    init {
        viewModelScope.launch {
            mutablestate.value.data?.copy(
                isAuthenticated = firebaseAuth.currentUser != null
            )
        }
    }


}