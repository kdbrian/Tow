package com.kdbrian.tow.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kdbrian.tow.presentation.ui.util.AppDataStore
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModules = module {

    single<Json> {
        Json { ignoreUnknownKeys = true }
    }


//    single {
//        Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//    }


    single {
        AppDataStore(
            get()
        )
    }

    single {
        LocationServices.getFusedLocationProviderClient(get<Context>())
    }


}