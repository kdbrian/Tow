package com.kdbrian.tow.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.presentation.nav.AddVehicleRoute
import com.kdbrian.tow.presentation.ui.components.VehicleItem
import com.kdbrian.tow.presentation.ui.state.MyVehiclesViewModel
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVehicles(
    navHostController: NavHostController = rememberNavController()
) {

    val vehiclesViewModel = koinViewModel<MyVehiclesViewModel>()
    val uiState by vehiclesViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vehiclesViewModel.loadMyVehicles()
    }

    Scaffold(
        containerColor = LocalAppColor.current,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalAppColor.current,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("My Vehicles")
                            }
                            append("\n")
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(60)
                                )
                            ) {
                                append("saved ${uiState.mine?.size ?: ""} vehicles.")
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navHostController.navigate(AddVehicleRoute)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        Box(
            modifier = Modifier.padding(pd)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                uiState.mine?.let { mine ->

                    itemsIndexed(mine) { index, v ->
                        key(index) {
                            VehicleItem(
                                vehicle = v
                            )
                        }


                    }


                }


            }


        }
    }

}


@Preview
@Composable
private fun MyVehiclesPrev() {
    App {
        MyVehicles()
    }
}
