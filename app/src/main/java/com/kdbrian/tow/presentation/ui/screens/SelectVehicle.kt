package com.kdbrian.tow.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.data.local.datastore.VehicleDataStore
import com.kdbrian.tow.domain.model.VehicleDto
import com.kdbrian.tow.presentation.ui.components.TowCustomInputField
import com.kdbrian.tow.presentation.ui.components.VehicleItem
import com.kdbrian.tow.presentation.ui.state.SelectVehicleScreenModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SelectVehicle(
    navHostController: NavHostController = rememberNavController(),
    selectVehicleScreenModel: SelectVehicleScreenModel = koinViewModel()
) {

    val uiState by selectVehicleScreenModel.state.collectAsState()
    val context = LocalContext.current
    val vehicleDataStore = koinInject<VehicleDataStore>()
    val savedVehicles by vehicleDataStore.loadVehicles(context)
        .collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        selectVehicleScreenModel.loadModels()
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
                            text = "Select Vehicle",
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
                },
                actions = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(12.dp)
                .verticalScroll(scrollState)
        ) {

            TowCustomInputField(
                value = uiState.query,
                modifier = Modifier.padding(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                }
            )


            uiState.models?.let { vehicles ->
                val vehicleDtos = vehicles.map {
                    VehicleDto(
                        vehicle = it,
                        isSaved = savedVehicles.contains(it)
                    )
                }

                vehicleDtos.forEachIndexed { index, dto ->
                    key(index) {
                        VehicleItem(
                            vehicleDto = dto,
                            onSaved = { model ->
                                coroutineScope.launch {
                                    if (savedVehicles.any { it.model == model }) {
                                        vehicleDataStore.removeVehicleByModel(model)
                                    } else {
                                        vehicleDataStore.addVehicle(dto.vehicle)
                                    }
                                }
                            },
                            onSelect = {
                                selectVehicleScreenModel.setSelected(dto.vehicle)
                            }
                        )
                    }
                }


            }

        }
    }

}


@Preview
@Composable
private fun SelectVehiclePrev() {
    App {
        SelectVehicle()
    }
}