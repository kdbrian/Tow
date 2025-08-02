package com.kdbrian.tow.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppUser (
    val email: String?=null,
    val phoneNumber: String?=null,
    val photoUrl: String?=null,
    val displayName: String?=null,
    val uid: String?=null,
    val accountAge: Long?=null,
)