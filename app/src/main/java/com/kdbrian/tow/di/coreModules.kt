package com.kdbrian.tow.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreModules = module {

    single<Json>{
        Json { ignoreUnknownKeys = true }
    }




}