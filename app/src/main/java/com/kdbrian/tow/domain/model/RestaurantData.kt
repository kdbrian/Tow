package com.kdbrian.tow.domain.model

/**
 * Data class to represent the details of a restaurant.
 *
 * @param imageUrl The URL of the image for the restaurant.
 * @param name The name of the restaurant (e.g., "Kentucky Fried Chicken(KFC)").
 * @param nearestDistance The distance to the nearest branch (e.g., "Nearest 21KM").
 * @param branchesCount The number of branches (e.g., "Over 30 branches").
 */
data class RestaurantData(
    val imageUrl: String,
    val name: String,
    val nearestDistance: String,
    val branchesCount: String
)