package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EvStation
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.domain.model.Vehicle
import com.kdbrian.tow.domain.model.VehicleDto

/**
 * Composable to display a summary card for a vehicle.
 * It uses the [Vehicle] data class to populate its details.
 *
 * @param vehicle The [Vehicle] object containing the details to display.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun VehicleSummary(
    modifier: Modifier = Modifier,
    vehicle: VehicleDto,
    shape: Shape = RoundedCornerShape(24.dp)
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        contentColor = Color(0xFF07090F),
        color = Color(0xFFF5FBEF),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 21.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Summary",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(40),
                    fontFamily = LocalFontFamily.current,
//                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Display Vehicle Model
            SummaryDetailRow(
                icon = Icons.Default.DirectionsCar,
                label = "Model",
                value = vehicle.vehicle.model
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Display Plate Number
            SummaryDetailRow(
                icon = Icons.Default.DirectionsCar, // Reusing car icon, could be a plate icon
                label = "Plate Number",
                value = vehicle.plateNumber ?: ""
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Display Mileage
            SummaryDetailRow(
                icon = Icons.Default.Speed,
                label = "Mileage",
                value = "${vehicle.vehicle.mileage} KM" // Assuming KM, adjust as needed
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Display Fuel Tank Capacity or Battery Capacity
            if (vehicle.vehicle.fuelTankCapacity != null) {
                SummaryDetailRow(
                    icon = Icons.Default.LocalGasStation,
                    label = "Fuel Capacity",
                    value = "${vehicle.vehicle.fuelTankCapacity} Liters"
                )
            } else if (vehicle.vehicle.batteryCapacityKWh != null) {
                SummaryDetailRow(
                    icon = Icons.Default.EvStation,
                    label = "Battery Capacity",
                    value = "${vehicle.vehicle.batteryCapacityKWh} kWh"
                )
            }
        }
    }
}


/**
 * Helper Composable to display a single detail row within the summary.
 *
 * @param icon The icon for the detail.
 * @param label The label for the detail (e.g., "Model", "Mileage").
 * @param value The value associated with the label.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
private fun SummaryDetailRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String = LoremIpsum(2).values.joinToString(),
    value: String = LoremIpsum(2).values.joinToString()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
//                    color = Color.Black,
                    fontSize = 18.sp
                )
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun VehicleSummaryPreview() {
    // Example ICE Vehicle
    val iceVehicle = VehicleDto(
        plateNumber = "ABC-123",
        vehicle = Vehicle(
            model = "Toyota Camry",
            mileage = 55000,
            fuelTankCapacity = 60.0
        )
    )

    // Example EV Vehicle
    val evVehicle = VehicleDto(
        plateNumber = "EV-456",
        vehicle = Vehicle(
            model = "Tesla Model 3",
            mileage = 25000,
            batteryCapacityKWh = 75.0
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("ICE Vehicle Summary:", style = MaterialTheme.typography.titleMedium)
        VehicleSummary(vehicle = iceVehicle)
        Spacer(modifier = Modifier.height(24.dp))

        Text("EV Vehicle Summary:", style = MaterialTheme.typography.titleMedium)
        VehicleSummary(vehicle = evVehicle)
    }
}

