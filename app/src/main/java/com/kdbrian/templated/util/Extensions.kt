package com.kdbrian.templated.util


import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

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
