package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.libraries.places.api.model.Place
import com.kdbrian.tow.App
import com.kdbrian.tow.presentation.ui.state.PlaceSelectFormViewModel
import org.koin.compose.viewmodel.koinViewModel


// Data class to hold the UI state for the Place Selection Form
data class PlaceSelectFormUiState(
    val query: String = "",
    val currentLatitude: Double? = null,
    val currentLongitude: Double? = null,
    val currentLocationName: String = "Unknown Location",
    val searchResults: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlaceSelectForm(modifier: Modifier = Modifier) {

    val query = rememberTextFieldState()
    var isEnabled by remember { mutableStateOf(true) }
    val placeSelectFormViewModel = koinViewModel<PlaceSelectFormViewModel>()

    val uiState by placeSelectFormViewModel.uiState.collectAsState()

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    LaunchedEffect(Unit) {
        if (!permissions.allPermissionsGranted) {
            permissions.launchMultiplePermissionRequest()
        } else {
            placeSelectFormViewModel.loadMyLocation()
        }
    }



    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        item {
            if (permissions.shouldShowRationale) {
                AppHint(
                    text = buildAnnotatedString {
                        append("We require location permissions to search for places.")
                    },
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        item {
            TowCustomInputField(
                onValueUpdate = placeSelectFormViewModel::updateQuery,
                modifier = Modifier.padding(12.dp),
                value = TextFieldState(uiState.query),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search

                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                },
                keyboardActions = KeyboardActions(onSearch = {
                    isEnabled = false
                }),
                enabled = isEnabled,
                placeholderText = "place/destination name"
            )
        }

        itemsIndexed(uiState.searchResults) { index, place ->
            key(index) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(4.dp),
                    shadowElevation = 3.dp
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(place.displayName ?: "")
                            append("\n")

                            append(place.openingHours.toString())
                            append("\n")

                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

            }
        }


    }
}

@Preview
@Composable
private fun PlaceSelectFormPrev() {
    App {
        PlaceSelectForm()
    }
}