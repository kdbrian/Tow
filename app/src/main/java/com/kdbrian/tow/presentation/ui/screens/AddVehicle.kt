package com.kdbrian.tow.presentation.ui.screens

import android.location.Location
import androidx.collection.mutableDoubleListOf
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowLeft
import androidx.compose.material.icons.automirrored.rounded.ArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckBoxOutlineBlank
import androidx.compose.material.icons.rounded.DirectionsCarFilled
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.presentation.ui.components.CustomArrowButton
import com.kdbrian.tow.presentation.ui.components.TowCustomInputField
import com.kdbrian.tow.presentation.ui.state.AddVehicleViewModel
import com.kdbrian.tow.util.getLocationName
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import timber.log.Timber

@Serializable
sealed class AddVehicleAction {

    @Serializable
    data object SelectPlace : AddVehicleAction()
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
@Composable
fun AddVehicle(
    navHostController: NavHostController = rememberNavController()
) {
    var isVehicleTypesVisible by remember { mutableStateOf(false) }
    val icon by remember {
        derivedStateOf {
            if (isVehicleTypesVisible)
                Icons.AutoMirrored.Rounded.ArrowLeft
            else
                Icons.AutoMirrored.Rounded.ArrowRight
        }
    }

    val vehicleViewModel = koinViewModel<AddVehicleViewModel>()
    val uiState by vehicleViewModel.uiState.collectAsState()


    val vehicleTypes = stringArrayResource(R.array.vehicle_types)
    val context = LocalContext.current
    var selectedType by remember {
        mutableIntStateOf(0)
    }
    var useCurrentLocation by remember { mutableStateOf(false) }
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )
    LaunchedEffect(uiState) {
        Timber.d("reached")
        if (permissionsState.allPermissionsGranted) {
            vehicleViewModel.loadMyLocation()
        } else {
            useCurrentLocation = false
            permissionsState.launchMultiplePermissionRequest()

        }
    }

    var isOptionsVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    var action by remember {
        mutableStateOf<AddVehicleAction?>(null)
    }

    LaunchedEffect(isOptionsVisible, action) {
        if (isOptionsVisible || action != null)
            bottomSheetState.show()
        else
            bottomSheetState.hide()
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
                    Column {
                        Text(
                            text = "Register Vehicle",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = LocalFontFamily.current
                        )

                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp)
        ) {

            item {
                var isServicesDropDownVisible by remember { mutableStateOf(false) }
                val icon by remember {
                    derivedStateOf {
                        if (isServicesDropDownVisible)
                            Icons.AutoMirrored.Rounded.ArrowLeft
                        else
                            Icons.AutoMirrored.Rounded.ArrowRight
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Model name",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Box {

                        TowCustomInputField(
                            value = uiState.model,
                            placeholderText = "select",
                            fieldShape = RoundedCornerShape(48.dp),
                            modifier = Modifier,
                            trailingIcon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = isServicesDropDownVisible,
                            onDismissRequest = { isServicesDropDownVisible = false }
                        ) {

                        }
                    }
                }
            }

            item {
                Spacer(Modifier.padding(10.dp))
            }


            item {

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Plate No.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    TowCustomInputField(
                        value = uiState.plateNo,
                        placeholderText = "",
                        fieldShape = RoundedCornerShape(48.dp),
                        modifier = Modifier,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.DirectionsCarFilled,
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            item {
                Spacer(Modifier.padding(10.dp))
            }


            item {


                val iconTint by remember {
                    derivedStateOf {
                        if (uiState.useMyLocation)
                            Icons.Rounded.Check to Color.Green
                        else
                            Icons.Rounded.CheckBoxOutlineBlank to Color.LightGray
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Location",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Surface(
                        color = Color.Transparent,
                        onClick = {
                            vehicleViewModel.setUseMyLocation(!uiState.useMyLocation)
                        }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(40)
                                    )
                                ) {
                                    append("Use current location ")
                                }
                                append("\n")
                                append(if (uiState.useMyLocation) uiState.location.text.toString() else "")
                            })

                            Icon(
                                imageVector = iconTint.first,
                                contentDescription = null,
                                tint = iconTint.second
                            )

                        }
                    }

                    AnimatedVisibility(!uiState.useMyLocation) {
                        Column {

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                            Box {
                                TowCustomInputField(
                                    value = uiState.location,
                                    placeholderText = "Select",
                                    enabled = false,
                                    fieldShape = RoundedCornerShape(48.dp),
                                    modifier = Modifier
                                        .clickable {
                                            action = AddVehicleAction.SelectPlace
                                        },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Rounded.DirectionsCarFilled,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.padding(10.dp))
            }

            item {


                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Box {

                        TowCustomInputField(
                            value = uiState.type,
                            placeholderText = "select",
                            enabled = false,
//                            value = TextFieldState(vehicleTypes[selectedType]),
                            fieldShape = RoundedCornerShape(48.dp),
                            modifier = Modifier.clickable {
                                isVehicleTypesVisible = !isVehicleTypesVisible
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = isVehicleTypesVisible,
                            onDismissRequest = { isVehicleTypesVisible = false }
                        ) {
                            vehicleTypes.forEachIndexed { index, type ->
                                DropdownMenuItem(
                                    text = { Text(text = type) },
                                    onClick = {
                                        isVehicleTypesVisible = false
                                        vehicleViewModel.setType(type)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.padding(10.dp))
            }

            item {
                CustomArrowButton(
                    text = "Save",
                    modifier = Modifier.wrapContentSize(),
                    buttonShape = RoundedCornerShape(32.dp)
                ) {
                    vehicleViewModel.addVehicle(
                        Vehicle(
                            model = uiState.model.text.toString(),
                            plateNumber = uiState.plateNo.text.toString(),
                            location = uiState.location.text.toString()
                        )
                    )
                }
            }


        }
    }

    if (action != null) {
        ModalBottomSheet(
            onDismissRequest = { action = null },
            sheetState = bottomSheetState
        ) {

            when (action) {
                AddVehicleAction.SelectPlace -> Unit
                null -> Unit
            }


        }
    }


}


@Preview
@Composable
private fun AddVehiclePrev() {
    App {
//        AddVehicle()
    }
}