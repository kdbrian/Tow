package com.kdbrian.tow.presentation.ui.state

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.kdbrian.tow.presentation.ui.components.PlaceSelectFormUiState
import com.kdbrian.tow.util.getLocationName
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(FlowPreview::class)
class PlaceSelectFormViewModel(
    private val placesClient: PlacesClient,
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    // Define the fields you want to retrieve for a Place.
    // Ensure these fields are enabled in your Google Cloud Project for the Places API.
    val placeFields = listOf(
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.EV_CHARGE_OPTIONS,
        Place.Field.FUEL_OPTIONS,
        Place.Field.CURRENT_OPENING_HOURS,
        Place.Field.LAT_LNG,
        Place.Field.ICON_URL,
        Place.Field.DISPLAY_NAME,
        Place.Field.ADDRESS // Added for better display
    )

    // Internal mutable state flow for UI updates
    private val _uiState = MutableStateFlow(PlaceSelectFormUiState())

    // Public immutable state flow for UI observation
    val uiState: StateFlow<PlaceSelectFormUiState> = _uiState.asStateFlow()

    init {
        // Observe changes in query and current location to trigger place searches
        viewModelScope.launch {
            _uiState
                .debounce(300L) // Debounce to avoid too many API calls while typing
                .distinctUntilChanged { old, new ->
                    // Only trigger search if query or current location changes significantly
                    old.query == new.query &&
                            old.currentLatitude == new.currentLatitude &&
                            old.currentLongitude == new.currentLongitude
                }
                .collect { state ->
                    // Trigger search only if there's a query and a valid current location
                    if (state.query.isNotEmpty() && state.currentLatitude != null && state.currentLongitude != null) {
                        searchPlaces(state.query, state.currentLatitude, state.currentLongitude)
                    } else if (state.query.isEmpty()) {
                        // Clear search results if query is empty
                        _uiState.value = _uiState.value.copy(searchResults = emptyList())
                    }
                }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun loadMyLocation() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    viewModelScope.launch {
                        val locationName = context.getLocationName(it.latitude, it.longitude)
                        _uiState.value = _uiState.value.copy(
                            currentLatitude = it.latitude,
                            currentLongitude = it.longitude,
                            currentLocationName = locationName ?: "",
                            isLoading = false
                        )
                    }
                } ?: run {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Could not retrieve current location. Make sure location services are enabled."
                    )
                    Log.w(TAG, "Last known location is null.")
                }
            }
            .addOnFailureListener { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to get current location: ${exception.message}"
                )
                Log.e(TAG, "Error getting last location: ${exception.message}", exception)
            }
    }

    private suspend fun searchPlaces(query: String, latitude: Double, longitude: Double) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        try {
            val textRequest = SearchByTextRequest.builder(query, placeFields)
                .setMaxResultCount(20)
                .setLocationRestriction(
                    CircularBounds.newInstance(
                        LatLng(latitude, longitude),
                        10000.0 // 10 km radius
                    )
                )
                .build()

            val textResponse = placesClient.searchByText(textRequest).await()

            _uiState.value = _uiState.value.copy(
                searchResults = textResponse.places,
                isLoading = false
            )
        } catch (e: ApiException) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Places API error: ${e.statusCode} - ${e.message}"
            )
            Log.e(TAG, "Places API error: ${e.statusCode} - ${e.message}", e)
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "An unexpected error occurred: ${e.message}"
            )
            Log.e(TAG, "Unexpected error during place search: ${e.message}", e)
        }
    }

    fun updateQuery(newQuery: String) {
        _uiState.value = _uiState.value.copy(query = newQuery)
    }

    companion object {
        private const val TAG = "PlaceSelectFormViewModel"
    }
}