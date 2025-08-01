package com.kdbrian.tow

import android.app.Application
import com.kdbrian.tow.di.coreModules
import com.kdbrian.tow.di.firebaseModules
import com.kdbrian.tow.di.vehicleModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class Tow : Application() {
    override fun onCreate() {
        super.onCreate()

        ///logger
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        //koin declaration
        startKoin {
            androidContext(this@Tow)
            androidLogger()

            modules(
                coreModules,
                firebaseModules,
                vehicleModules,
            )
        }
    }
}