package com.kdbrian.tow.di

import com.kdbrian.tow.data.local.VehicleService
import com.kdbrian.tow.data.local.datastore.VehicleDataStore
import com.kdbrian.tow.data.remote.impl.VehicleRepoImpl
import com.kdbrian.tow.presentation.ui.state.MyVehiclesViewModel
import com.kdbrian.tow.presentation.ui.state.AddVehicleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val vehicleModules = module {


    single<VehicleService> {
        VehicleRepoImpl(
            get(),
            get(),
        )
    }

    single {
        VehicleDataStore(get())
    }

    viewModel {
        AddVehicleViewModel(
            vehicleService = get(),
            context = get(),
            fusedLocationProviderClient = get()
        )
    }

    viewModel {
        MyVehiclesViewModel(
            vehicleService = get()
        )
    }

}