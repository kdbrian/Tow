package com.kdbrian.tow.di

import android.app.Activity
import com.kdbrian.tow.data.remote.impl.AuthRepoImpl
import com.kdbrian.tow.domain.repo.AuthRepo
import com.kdbrian.tow.presentation.ui.state.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val authModule = module {

    single<AuthRepo> { (activity: Activity) ->
        AuthRepoImpl(auth = get(), activity = activity)
    }


    viewModel { (activity: Activity) ->
        AuthViewModel(authRepo = get { parametersOf(activity) })
    }

}