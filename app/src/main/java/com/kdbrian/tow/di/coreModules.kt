package com.kdbrian.tow.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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


}