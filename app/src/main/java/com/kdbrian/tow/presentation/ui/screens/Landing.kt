package com.kdbrian.tow.presentation.ui.screens

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.presentation.nav.EateriesRoute
import com.kdbrian.tow.presentation.nav.MyVehiclesRoute
import com.kdbrian.tow.presentation.nav.ProfileRoute
import com.kdbrian.tow.presentation.nav.RequestHistoryRoute
import com.kdbrian.tow.presentation.nav.RequestServiceRoute
import com.kdbrian.tow.presentation.ui.components.CustomArrowButton
import com.kdbrian.tow.presentation.ui.components.LoginAction
import com.kdbrian.tow.presentation.ui.components.MapCard
import com.kdbrian.tow.presentation.ui.components.ServiceCard
import com.kdbrian.tow.presentation.ui.state.LandingScreenModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Landing(
    navHostController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var createAccountVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val firebaseAuth = koinInject<FirebaseAuth>()
    val activity = context as Activity
    val authViewModel = koinViewModel<AuthViewModel>(parameters = { parametersOf(activity) })
    val landingScreenModel = koinViewModel<LandingScreenModel>()
    val uiState by landingScreenModel.state.collectAsState()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = LocalAppColor.current,
    ) { pd ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Good day",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontFamily = LocalFontFamily.current,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 48.sp
                        )
                    )

                    Text(
                        text = "Service, drinks & food at your comfort.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = LocalFontFamily.current,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(40)
                        )
                    )
                }

                Surface(
                    onClick = {
                        if (uiState.data?.isAuthenticated != true) {
                            createAccountVisible = true
                        } else {
                            navHostController.navigate(ProfileRoute)
                        }
                    },
                    shape = CircleShape,
                    border = BorderStroke(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFF3D9B1),
                                Color(0xFFF27059),
                            )
                        )
                    ),
                    shadowElevation = (-3).dp
                ) {
                    Box(
                        modifier = Modifier.size(50.dp)
                    ) {
                        AsyncImage(
                            model = firebaseAuth.currentUser?.photoUrl,
                            placeholder = painterResource(R.drawable.cars),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null,
                            error = painterResource(R.drawable.current_premium),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }

            ServiceCard(
                title = "Request Service",
                supportText = buildAnnotatedString { },
                backgroundImagePainter = painterResource(R.drawable.tow_truck)
            ) {
                navHostController.navigate(RequestServiceRoute)
            }

            AnimatedVisibility(false) {
                MapCard(
                    mapImagePainter = painterResource(R.drawable.geographical_map),
                    locationText = LoremIpsum(2).values.joinToString(),
                    searchRadius = 3.toString()
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomArrowButton(
                    text = "Activity",
                    icon = Icons.Rounded.Schedule
                ) { navHostController.navigate(RequestHistoryRoute) }

                AnimatedContent((uiState.data?.numberOfVehicles ?: -1) > 0) {
                    if (it) {
                        CustomArrowButton(
                            text = "My Cars",
                            icon = Icons.Rounded.DirectionsCar
                        ) { navHostController.navigate(MyVehiclesRoute) }
                    } else {
                        ServiceCard(
                            backgroundImagePainter = painterResource(R.drawable.cars),
                            title = "Add Vehicles",
                            supportText = buildAnnotatedString {
                                append("Keep track of your ownership game.")
                            }
                        ) {
                            navHostController.navigate(MyVehiclesRoute)
                        }
                    }
                }


            }

            ServiceCard(
                title = "Eateries",
                supportText = buildAnnotatedString {
                    append("Find the best eateries near you.")
                },
                backgroundImagePainter = painterResource(R.drawable.fast_foods),
                onClick = { navHostController.navigate(EateriesRoute) }
            )

        }
    }

    if (createAccountVisible) {
        ModalBottomSheet(
            onDismissRequest = { createAccountVisible = false },
            sheetState = bottomSheetState
        ) {
            LoginAction(
                authViewModel = authViewModel,
                onDismiss = { createAccountVisible = false },
            )

        }
    }


}

@Preview
@Composable
private fun LandingPrev() {
    App {
        Landing()
    }
}