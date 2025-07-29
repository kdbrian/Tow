package com.kdbrian.templated.presentation.ui.state

import com.google.firebase.auth.FirebaseUser

data class LandingUiState(
    val isAuthenticated: Boolean = false,
    val firebaseUser: FirebaseUser? = null,
    val lastLogin: Long? = null
)