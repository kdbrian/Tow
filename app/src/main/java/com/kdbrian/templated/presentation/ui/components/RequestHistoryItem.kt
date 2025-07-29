package com.kdbrian.templated.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.domain.model.RequestHistoryData
import com.kdbrian.templated.util.formatDateFromLong


/**
 * A composable item to display a single request history entry.
 *
 * @param requestHistory The [RequestHistory] data to display.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun RequestHistoryItem(
    modifier: Modifier = Modifier,
    requestHistory: RequestHistoryData,
    onClick : () -> Unit = {}
) {
    Surface(
        color = Color.Transparent,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp) // Inner padding
        ) {
            // Top Row: Service Name and Date/Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = requestHistory.serviceName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                )

                Text(
                    text = formatDateFromLong(timestamp = requestHistory.dateTimestamp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Space between rows

            // Bottom Row: Vehicle Number and Total Cost
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = requestHistory.vehicleNumber,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = requestHistory.currency,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "%,.0f".format(requestHistory.totalCost), // Format cost with comma, no decimals
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    )
                }
            }
            // Horizontal line (Divider) - as seen in the image below the text
            Spacer(modifier = Modifier.height(8.dp))
            // You might need a custom composable for the exact line style if Material3 Divider isn't enough
            // For simplicity, a thin Box can simulate it.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestHistoryItemPreview() {
    val sampleRequest = RequestHistoryData(
        id = "req123",
        serviceName = "Toll Service",
        dateTimestamp = 1721898600000L, // July 24, 2025 07:30:00 AM GMT+3 (example timestamp)
        totalCost = 4000.0,
        currency = "Ksh",
        vehicleNumber = "VXY123R",
        providerId = "prov456",
        vehicleId = "veh789"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RequestHistoryItem(requestHistory = sampleRequest)
        Spacer(modifier = Modifier.height(16.dp))
        // Another example
        RequestHistoryItem(
            requestHistory = sampleRequest.copy(
                serviceName = "Car Wash",
                totalCost = 550.0,
                vehicleNumber = "ABC789X",
                dateTimestamp = System.currentTimeMillis()
            )
        )
    }
}