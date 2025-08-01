package com.kdbrian.tow.di

import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.data.local.datastore.VehicleDataStore
import com.kdbrian.tow.data.local.impl.VehicleRepoImpl
import com.kdbrian.tow.presentation.ui.state.SelectVehicleScreenModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vehicleModules = module {


    single<VehicleService> {
        VehicleRepoImpl(
            get(),
            get(),
        )
    }

    viewModel<SelectVehicleScreenModel> {
        SelectVehicleScreenModel(get())
    }

    single {
        VehicleDataStore(get())
    }

}