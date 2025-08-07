package com.kdbrian.tow

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.kdbrian.tow.di.authModule
import com.kdbrian.tow.di.coreModules
import com.kdbrian.tow.di.firebaseModules
import com.kdbrian.tow.di.landingModules
import com.kdbrian.tow.di.placesModules
import com.kdbrian.tow.di.vehicleModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Tow : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.placesApiKey.isNotEmpty()){
            Places.initialize(this, BuildConfig.placesApiKey)
        }

        ///logger
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        //koin declaration
        startKoin {
            androidContext(this@Tow)
            androidLogger()

            modules(
                authModule,
                coreModules,
                firebaseModules,
                landingModules,
                placesModules,
                vehicleModules,

            )
        }
    }
}