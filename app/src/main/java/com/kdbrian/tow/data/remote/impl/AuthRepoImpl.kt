package com.kdbrian.tow.data.remote.impl

import android.app.Activity
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kdbrian.tow.domain.repo.AuthRepo
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.concurrent.TimeUnit


class AuthRepoImpl(
    private val auth: FirebaseAuth,
    private val activity: Activity
) : AuthRepo {

    private var currentVerificationId: String? = null
    private var verificationInitiationDeferred: CompletableDeferred<Result<Boolean>>? = null

    override suspend fun phoneNumberLogin(number: String): Result<Boolean> {
        verificationInitiationDeferred = CompletableDeferred()
        currentVerificationId = null

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Timber.d("onVerificationCompleted: Auto-verification successful.")
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Timber.d("onVerificationCompleted: Sign-in with credential successful.")
                                verificationInitiationDeferred?.complete(Result.success(true))
                            } else {
                                Timber.e("onVerificationCompleted: Sign-in with credential failed: ${task.exception}")
                                verificationInitiationDeferred?.complete(
                                    Result.failure(
                                        task.exception
                                            ?: Exception("Unknown error during auto-sign-in")
                                    )
                                )
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Timber.e("onVerificationFailed: ${e.message}", e)
                    verificationInitiationDeferred?.complete(Result.failure(e))
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Timber.d("onCodeSent: Code sent to $number. Verification ID: $verificationId")
                    currentVerificationId = verificationId
                    verificationInitiationDeferred?.complete(Result.success(true))
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        return verificationInitiationDeferred!!.await()
    }

    override suspend fun verifyCode(code: String): Result<Boolean> {
        val verificationId = currentVerificationId
        if (verificationId == null) {
            val errorMessage =
                "Verification ID is null. Phone number verification not initiated or failed."
            Timber.e(errorMessage)
            return Result.failure(IllegalStateException(errorMessage))
        }

        return try {
            Timber.d("verifyCode: Attempting to get credential with verificationId: $verificationId and code: $code")
            val credential = PhoneAuthProvider.getCredential(verificationId, code)

            val authResult = auth.signInWithCredential(credential).await()
            Timber.d("verifyCode: Sign-in with credential successful. User phone: ${authResult.user?.phoneNumber}")
            Result.success(authResult.user != null)
        } catch (e: Exception) {
            Timber.d("verifyCode: Sign-in with credential failed: ${e.message}")
            Result.failure(e)
        }
    }
}
