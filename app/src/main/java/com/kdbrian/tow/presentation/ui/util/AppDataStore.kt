package com.kdbrian.tow.presentation.ui.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kdbrian.tow.BuildConfig

// At the top level of your kotlin file:
val Context.appDataStore by preferencesDataStore(name = "${BuildConfig.APPLICATION_ID}_AppDataStore")

class AppDataStore(
    private val context: Context
) {

    private val _firstTimeOnAppKey = booleanPreferencesKey("firstTimeOnApp")
    suspend fun setFirstTimeOnApp(isFirstTime: Boolean) {
        context.appDataStore.edit {
            it[_firstTimeOnAppKey] = isFirstTime
        }
    }


    companion object {
        const val dataStoreName = "${BuildConfig.APPLICATION_ID}_appDataStore"
    }


}