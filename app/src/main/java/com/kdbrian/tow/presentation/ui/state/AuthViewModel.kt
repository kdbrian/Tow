package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdbrian.tow.data.remote.impl.AuthRepoImpl
import com.kdbrian.tow.domain.repo.AuthRepo
import com.kdbrian.tow.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Boolean>>(Resource.Nothing())
    val loginState: StateFlow<Resource<Boolean>> = _loginState

    private val _codeVerificationState = MutableStateFlow<Resource<Boolean>>(Resource.Loading())
    val codeVerificationState: StateFlow<Resource<Boolean>> = _codeVerificationState

    fun startPhoneLogin(phone: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            try {
                val result = authRepo.phoneNumberLogin(phone)
                _loginState.value = if (result.isSuccess) {
                    Resource.Success(result.getOrNull())
                } else {
                    Resource.Error(result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.message)
            }
        }
    }

    fun verifyCode(code: String) {
        _codeVerificationState.value = Resource.Loading()
        if (authRepo is AuthRepoImpl) {
            try {
                val result = authRepo.verifyCode(code)
                _codeVerificationState.value = if (result.isSuccess) {
                    Resource.Success(result.getOrNull())
                } else {
                    Resource.Error(result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _codeVerificationState.value = Resource.Error(e.message)
            }
        } else {
            _codeVerificationState.value = Resource.Error("Unsupported AuthRepo")
        }
    }
}

