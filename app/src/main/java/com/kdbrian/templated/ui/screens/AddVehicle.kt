package com.kdbrian.templated.ui.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalAppColor
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R
import com.kdbrian.templated.ui.components.CustomArrowButton
import com.kdbrian.templated.ui.components.TowCustomInputField


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddVehicle(
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

    val vehicleTypes = stringArrayResource(R.array.vehicle_types)
    var selectedType by remember {
        mutableIntStateOf(0)
    }


    var isOptionsVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    LaunchedEffect(isOptionsVisible) {
        if (isOptionsVisible)
            bottomSheetState.show()
        else
            bottomSheetState.hide()
    }

    Scaffold(
        containerColor = LocalAppColor.current,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Request Service",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = LocalFontFamily.current
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(fontSize = 16.sp)) {
                                    append("Estimated Feedback Time ")
                                }

                                withStyle(SpanStyle(fontSize = 8.sp, color = Color.Green)) {
                                    append("38mins")
                                }
                            },
                            fontWeight = FontWeight(50),
                            fontFamily = LocalFontFamily.current
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
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
                            placeholderText = "select",
                            enabled = false,
                            fieldShape = RoundedCornerShape(48.dp),
                            modifier = Modifier.clickable {
                                isServicesDropDownVisible = !isServicesDropDownVisible
                            },
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
                        placeholderText = "type",
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

                var useMine by remember {
                    mutableStateOf(false)
                }

                val iconTint by remember {
                    derivedStateOf {
                        if (useMine)
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
                        onClick = {
                            useMine = !useMine
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
                                append("")
                            })

                            Icon(
                                imageVector = iconTint.first,
                                contentDescription = null,
                                tint = iconTint.second
                            )

                        }
                    }

                    AnimatedVisibility(!useMine) {
                        Column {

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                            Box {
                                TowCustomInputField(
                                    placeholderText = "Select",
                                    enabled = false,
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
                            placeholderText = "select",
                            enabled = false,
                            value = TextFieldState(vehicleTypes[selectedType]),
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
                                    onClick = { selectedType = index }
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
                ) { }
            }


        }
    }

}

@Preview
@Composable
private fun AddVehiclePrev() {
    App {
        AddVehicle()
    }
}