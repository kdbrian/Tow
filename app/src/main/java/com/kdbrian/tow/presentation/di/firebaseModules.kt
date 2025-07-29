package com.kdbrian.tow.presentation.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val firebaseModules = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

}