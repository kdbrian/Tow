package com.kdbrian.templated.domain.model

/**
 * Data class to represent the details of a food item.
 *
 * @param imageUrl The URL of the image for the food item.
 * @param title The name of the food item (e.g., "Tripple Drumsticks").
 * @param subtitle The provider or description (e.g., "Served by KFC").
 * @param price The price of the food item (e.g., "3,000").
 * @param currency The currency symbol (e.g., "Kes").
 */
data class FoodItemData(
    val imageUrl: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val currency: String = "Kes"
)