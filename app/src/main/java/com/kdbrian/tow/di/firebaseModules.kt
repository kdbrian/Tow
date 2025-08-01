package com.kdbrian.tow.di

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kdbrian.tow.data.remote.impl.AuthRepoImpl
import com.kdbrian.tow.domain.repo.AuthRepo
import org.koin.dsl.module

val firebaseModules = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
}