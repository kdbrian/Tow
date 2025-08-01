package com.kdbrian.tow.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kdbrian.tow.presentation.ui.screens.AddVehicle
import com.kdbrian.tow.presentation.ui.screens.Eateries
import com.kdbrian.tow.presentation.ui.screens.Landing
import com.kdbrian.tow.presentation.ui.screens.MyVehicles
import com.kdbrian.tow.presentation.ui.screens.Profile
import com.kdbrian.tow.presentation.ui.screens.RequestDetails
import com.kdbrian.tow.presentation.ui.screens.RequestHistory
import com.kdbrian.tow.presentation.ui.screens.RequestService
import com.kdbrian.tow.presentation.ui.screens.SelectVehicle
import com.kdbrian.tow.presentation.ui.screens.Services
import com.kdbrian.tow.presentation.ui.state.SelectVehicleScreenModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainNav() {

    val navController = rememberNavController()
//    val vehicleScreenModel = koinViewModel<SelectVehicleScreenModel>()


    NavHost(navController = navController, startDestination = LandingRoute) {

        composable<LandingRoute> {
            Landing(navHostController = navController)
        }

        composable<ProfileRoute> {
            Profile(navHostController = navController)
        }

        composable<MyVehiclesRoute> {
            MyVehicles(navHostController = navController)
        }

        composable<AddVehicleRoute> {
            AddVehicle(navHostController = navController)
        }

        composable<EateriesRoute> {
            Eateries(navHostController = navController)
        }

        composable<RequestServiceRoute> {
            RequestService(navHostController = navController)
        }

        composable<ServicesRoute> {
            Services(navHostController = navController)
        }

        composable<RequestHistoryRoute> {
            RequestHistory(navHostController = navController)
        }

        composable<RequestDetailsRoute> {
            //load id
            RequestDetails(navHostController = navController)
        }

        composable<SelectVehicleRoute> {
            //load id
            SelectVehicle(
                navHostController = navController,
            )
        }

    }


}