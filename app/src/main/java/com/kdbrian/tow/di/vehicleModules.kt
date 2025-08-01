package com.kdbrian.tow.di

import com.kdbrian.tow.data.local.datastore.VehicleDataStore
import org.koin.dsl.module

val vehicleModules = module {


//    single<VehicleService> {
//        VehicleRepoImpl(
//            get(),
//            get(),
//        )
//    }

//    viewModel<SelectVehicleScreenModel> {
//        SelectVehicleScreenModel(get())
//    }

    single {
        VehicleDataStore(get())
    }

}