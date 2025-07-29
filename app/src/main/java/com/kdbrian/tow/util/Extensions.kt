package com.kdbrian.tow.util


import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extension function on [Context] to get the display name of a file from its [Uri].
 * This is particularly useful for content URIs obtained from file pickers.
 *
 * @param uri The [Uri] of the file.
 * @return The display name of the file, or null if it cannot be determined.
 */
fun Context.getFileName(uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        // Use the contentResolver from the Context
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    result = it.getString(nameIndex)
                }
            }
        }
    }
    if (result == null) {
        // Fallback to extracting from path if content resolver fails or scheme is not "content"
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != -1) {
            result = result?.substring(cut!! + 1)
        }
    }
    return result
}

/**
 * Formats a given long timestamp into a date string in the format "WED, Aug 25 2024".
 * formats include "dd-MM-yyyy hh:mm a", default "EEE, MMM dd yyyy"
 * @param timestamp The timestamp in milliseconds.
 * @return The formatted date string.
 */
fun formatDateFromLong(format: String = "EEE, MMM dd yyyy", timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}


/**
 * Extension function on [Context] to get a human-readable location name
 * from latitude and longitude coordinates using Geocoder.
 *
 * @param latitude The latitude of the location.
 * @param longitude The longitude of the location.
 * @return A string representing the location name (e.g., city, address), or null if not found or an error occurs.
 */
fun Context.getLocationName(latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(this, Locale.getDefault())
    var locationName: String? = null

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For API 33 and above, use the asynchronous geocode method
            // Note: This is a suspend function and needs to be called from a coroutine scope.
            // For a direct synchronous call in a non-suspend context, it's more complex.
            // For simplicity in this example, we'll illustrate the synchronous fallback for older APIs.
            // In a real app, you'd handle this asynchronously.
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            locationName = addresses?.firstOrNull()?.locality ?: addresses?.firstOrNull()?.featureName
        } else {
            // For older APIs, use the synchronous geocode method
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            locationName = addresses?.firstOrNull()?.locality ?: addresses?.firstOrNull()?.featureName
        }
    } catch (e: IOException) {
        // Handle network or other I/O errors
        e.printStackTrace()
        locationName = null
    } catch (e: IllegalArgumentException) {
        // Handle invalid latitude/longitude
        e.printStackTrace()
        locationName = null
    }
    return locationName
}

