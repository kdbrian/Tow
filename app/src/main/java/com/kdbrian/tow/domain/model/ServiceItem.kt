package com.kdbrian.tow.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ServiceItem(
    val id: String,
    val code: String,
    val name: String,
    val description: String,
    val tags: List<String>,
) {
    companion object {
        val demoServices = listOf(
            ServiceItem(
                id = "1",
                code = "SVC123F",
                name = "Oil Change",
                description = "Change oil and filter",
                tags = listOf("Oil", "Filter")
            ),
            ServiceItem(
                id = "2",
                code = "SVC123F",
                name = "Tire Rotation",
                description = "Rotate tires",
                tags = listOf("Tire", "Rotation")
            ),
            ServiceItem(
                id = "3",
                code = "SVC123F",
                name = "Brake Inspection",
                description = "Inspect brakes",
                tags = listOf("Brake", "Inspection")
            ),
        )
    }
}
