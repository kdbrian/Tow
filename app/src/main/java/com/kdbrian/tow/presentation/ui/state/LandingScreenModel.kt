package com.kdbrian.tow.presentation.ui.state

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.data.remote.impl.AuthRepoImpl
import com.kdbrian.tow.domain.repo.AuthRepo
import com.kdbrian.tow.util.Resource
import com.kdbrian.tow.util.ScreenModel
import com.kdbrian.tow.util.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "LandingScreenModel"


class LandingScreenModel(
    private val authRepo: AuthRepo,
    private val firebaseAuth: FirebaseAuth,
    private val vehicleService: VehicleService
) : ScreenModel<LandingUiState>() {

    override val mutablestate: UiState<LandingUiState>
        get() = MutableStateFlow(Resource.Nothing())

    init {
        viewModelScope.launch {
            mutablestate.emit(Resource.Loading())

            vehicleService
                .count(firebaseAuth.currentUser?.uid ?: "")
                .onSuccess {
                    Timber.d("Number of vehicles: $it")
                    mutablestate.value.data?.copy(
                        isAuthenticated = firebaseAuth.currentUser != null,
                        numberOfVehicles = it.toInt()
                    )
                }.onFailure {
                    mutablestate.emit(
                        Resource.Error(
                            message = it.message ?: "An unknown error occurred"
                        )
                    )
                }

        }
    }


    private val _loginState = MutableStateFlow<Resource<Boolean>>(Resource.Nothing())
    val loginState: StateFlow<Resource<Boolean>> = _loginState

    private val _codeVerificationState = MutableStateFlow<Resource<Boolean>>(Resource.Nothing())
    val codeVerificationState: StateFlow<Resource<Boolean>> = _codeVerificationState

    fun startPhoneLogin(phone: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            val resetUiTimeOut = 1200L

            try {
                val result = authRepo.phoneNumberLogin(phone)

                Timber.tag("result").d("${result.isSuccess}")
                Timber.tag("result").d("d -> ${result.getOrNull()}")
                Timber.tag("result").d("e -> ${result.exceptionOrNull()}")

                result.onSuccess {
                    _loginState.value = Resource.Success(it)
                }

                result.onFailure {
                    _loginState.value = Resource.Error(it.message)

                    //for UI
                    delay(resetUiTimeOut)
                    _loginState.value = Resource.Nothing()

                }
            } catch (e: Exception) {
                _loginState.value = Resource.Error(e.message)

                //for UI
                delay(resetUiTimeOut)
                _loginState.value = Resource.Nothing()
            }
        }
    }

    fun verifyCode(code: String) {
        val resetUi: () -> Unit = {
            viewModelScope.launch {
                delay(1200L)
                _codeVerificationState.value = Resource.Nothing()
            }
        }

        viewModelScope.launch {
            _codeVerificationState.value = Resource.Loading()

            if (authRepo is AuthRepoImpl) {
                try {
                    val result = authRepo.verifyCode(code)
                    result.onSuccess {
                        _codeVerificationState.value =
                            Resource.Success(it)
                    }
                    result.onFailure {
                        _codeVerificationState.value =
                            Resource.Error(result.exceptionOrNull()?.message)
                        resetUi()
                    }
                } catch (e: Exception) {
                    _codeVerificationState.value = Resource.Error(e.message)
                    resetUi()
                }
            } else {
                _codeVerificationState.value = Resource.Error("Unsupported AuthRepo")
                resetUi()
            }
        }

    }


}