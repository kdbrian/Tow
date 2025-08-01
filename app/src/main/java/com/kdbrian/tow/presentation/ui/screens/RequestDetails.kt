package com.kdbrian.tow.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.domain.model.VehicleDto
import com.kdbrian.tow.presentation.ui.components.ServiceCard
import com.kdbrian.tow.presentation.ui.components.VehicleSummary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetails(
    navHostController: NavHostController = rememberNavController()
) {
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
                                append("saved 30 vehicles.")
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        Column(
            modifier = Modifier
                .padding(pd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            VehicleSummary(
                vehicle = VehicleDto(vehicle = Vehicle.dreamCar,plateNumber = null)
            )

            ServiceCard(
                title = "Delayed",
                backgroundImagePainter = painterResource(R.drawable.time_due)
            ) { }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ){
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF009F5A),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Checkout", fontFamily = LocalFontFamily.current)
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBC3908),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel", fontFamily = LocalFontFamily.current)
                }

            }


            ServiceCard(
                title = "Premium",
                backgroundImagePainter = painterResource(R.drawable.current_premium)
            ){

            }

        }
    }
}

