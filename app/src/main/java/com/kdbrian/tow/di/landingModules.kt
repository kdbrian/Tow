package com.kdbrian.tow.di

import com.kdbrian.tow.presentation.ui.state.LandingScreenModel
import org.koin.dsl.module

val landingModules = module {

    single {
        LandingScreenModel(
            authRepo = get(),
            firebaseAuth = get(),
            vehicleService = get()
        )
    }
}