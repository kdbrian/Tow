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
import java.util.concurrent.TimeUnit

class AuthRepoImpl(
    private val auth: FirebaseAuth,
    private val activity: Activity
) : AuthRepo {

    private var verificationId: String? = null
    private var verificationDeferred: CompletableDeferred<Result<Boolean>>? = null

    override suspend fun phoneNumberLogin(number: String): Result<Boolean> {
        verificationDeferred = CompletableDeferred()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                verificationDeferred?.complete(Result.success(true))
                            } else {
                                verificationDeferred?.complete(Result.failure(task.exception ?: Exception("Unknown error")))
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    verificationDeferred?.complete(Result.failure(e))
                }

                override fun onCodeSent(vid: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = vid
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        return verificationDeferred!!.await()
    }

    fun verifyCode(code: String): Result<Boolean> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            val task = Tasks.await(auth.signInWithCredential(credential))
            Result.success(task.user != null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
