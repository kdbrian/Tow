package com.kdbrian.tow.domain.repo

interface AuthRepo {
    suspend fun phoneNumberLogin(number: String): Result<Boolean>
}