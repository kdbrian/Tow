package com.kdbrian.tow.di

import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.kdbrian.tow.presentation.ui.state.PlaceSelectFormViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val placesModules = module {

    single<PlacesClient> {
        Places.createClient(get())
    }


    viewModel {
        PlaceSelectFormViewModel(
            get(),
            get(),
            get(),
        )
    }

}