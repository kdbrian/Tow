package com.kdbrian.tow.presentation.ui.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.ui.components.CustomArrowButton
import com.kdbrian.tow.ui.components.TimeOption
import com.kdbrian.tow.ui.components.TimeSelectionSection
import com.kdbrian.tow.ui.components.TowCustomInputField
import com.kdbrian.tow.util.getFileName


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RequestService(
    navHostController: NavHostController = rememberNavController()
) {


    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    // Launcher for selecting an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val permissionState = rememberPermissionState(
        permission =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else
                Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val context = LocalContext.current
    var isOptionsVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    LaunchedEffect(isOptionsVisible) {
        if (isOptionsVisible)
            bottomSheetState.show()
        else
            bottomSheetState.hide()
    }

    val timeOptions = listOf(
        TimeOption("45 Mins", 45 * 60 * 100),
        TimeOption("50 Mins", 50 * 60 * 100),
        TimeOption("1 hr", 60 * 60 * 100)
    )

    var selectedTime by remember { mutableStateOf<TimeOption?>(timeOptions[0]) } // Default to 45 Mins selected
    val selectedTimeState by remember {
        derivedStateOf {
            TextFieldState(selectedTime?.label ?: "")
        }
    }


    Scaffold(
        containerColor = LocalAppColor.current,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalAppColor.current,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                ),
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
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
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
                        text = "Service",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Box {

                        TowCustomInputField(
                            placeholderText = "tap to select",
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
                var isVehiclesDropDownVisible by remember { mutableStateOf(false) }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Vehicle",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Box {

                        TowCustomInputField(
                            placeholderText = "select below",
                            enabled = false,
                            fieldShape = RoundedCornerShape(48.dp),
                            modifier = Modifier.clickable {
                                isVehiclesDropDownVisible = !isVehiclesDropDownVisible
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.DirectionsCarFilled,
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = isVehiclesDropDownVisible,
                            onDismissRequest = { isVehiclesDropDownVisible = false }
                        ) {

                        }
                    }
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
                        color = Color.Transparent,
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
                                    placeholderText = "Select from map",
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
                        text = "Approximate Wait time",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )


                    TowCustomInputField(
                        placeholderText = "select",
                        enabled = false,
                        value = selectedTimeState,
                        fieldShape = RoundedCornerShape(48.dp),
                        modifier = Modifier.clickable {
                            isOptionsVisible = !isOptionsVisible
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Schedule,
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

                Surface(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(4.dp),
                    onClick = {

                        if (!permissionState.status.isGranted) {
                            permissionState.launchPermissionRequest()
                        } else {
                            galleryLauncher.launch("image/*")
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {

                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    ) {
                                        append("Attach Image")
                                    }
                                    append("\n")
                                })

                            Text(
                                text = buildAnnotatedString {

                                    val text by remember {
                                        derivedStateOf {
                                            if (imageUri != null && imageUri != Uri.EMPTY) {
                                                imageUri?.let { context.getFileName(it) } ?: ""
                                            } else if (permissionState.status.shouldShowRationale) {
                                                "Please grant permission to access images."
                                            } else {
                                                ""
                                            }
                                        }
                                    }

                                    withStyle(SpanStyle()) {
                                        append(text)
                                    }
                                },
                                modifier = Modifier.widthIn(220.dp)
                            )
                        }


                        Icon(
                            imageVector = Icons.Rounded.Image,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.padding(10.dp))
            }
            item {
                CustomArrowButton(
                    text = "Submit Request",
                    modifier = Modifier.wrapContentSize(),
                    buttonShape = RoundedCornerShape(32.dp)
                ) { }
            }
            item {
                Spacer(Modifier.padding(10.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Text(
                        text = LoremIpsum(24).values.joinToString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = LocalFontFamily.current,
                            fontWeight = FontWeight(30),
                            textAlign = TextAlign.Center,
                            color = Color.LightGray
                        ),
                        modifier = Modifier
                            .widthIn(220.dp)
                    )
                }
            }


        }
    }

    if (isOptionsVisible) {

        ModalBottomSheet(
            onDismissRequest = { isOptionsVisible = false },
            sheetState = bottomSheetState,
            containerColor = LocalAppColor.current,
            contentColor = Color.Black
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TimeSelectionSection(
                    title = "Select Time",
                    timeOptions = timeOptions,
                    selectedTime = selectedTime,
                    onTimeSelected = {
                        selectedTime = it
                        isOptionsVisible = false
                    }
                )

                Spacer(Modifier.padding(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(12.dp)
                        .background(color = Color(0xFFFF0101), shape = RoundedCornerShape(24.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                        .clip(
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Unlock Short Times")
                        Text("Access shorter service delivery times.")
                    }

                    Image(
                        painter = painterResource(R.drawable.image_removebg_preview),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.TopStart)
                    )
                }


            }
        }


    }
}

@Preview
@Composable
private fun RequestServicePrev() {
    App {
        RequestService()
    }
}