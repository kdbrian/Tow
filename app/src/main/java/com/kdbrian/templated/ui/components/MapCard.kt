package com.kdbrian.templated.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kdbrian.templated.R

/**
 * Composable to display a card containing a map placeholder and related text.
 *
 * @param locationText The text describing the location (e.g., "Lorem ipsum volutpat").
 * @param searchRadius The text indicating the search radius (e.g., "Search Radius 2KM").
 * @param mapImagePainter The painter for the map image. Use a placeholder if a real map isn't integrated yet.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun MapCard(
    modifier: Modifier = Modifier,
    mapImagePainter: androidx.compose.ui.graphics.painter.Painter,
    locationText: String,
    searchRadius: String
) {
    Surface (
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp), // Padding around the card itself
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation =4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = locationText,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
                Text(
                    text = searchRadius,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Map Image Placeholder
            Image(
                painter = mapImagePainter,
                contentDescription = "Map Placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp) // Fixed height for the map image
                    .clip(RoundedCornerShape(24.dp)) // Slightly rounded corners for the map image
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Text/Buttons (as seen in the image - "200 Contacts", "12 Services")
            // This part is illustrative and can be expanded with actual buttons/logic
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "200 Contacts",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFF007AFF), // Blue color
                        fontWeight = FontWeight.Medium
                    )
                )
                // You can replace this with actual buttons if needed, similar to BottomSheetContent
                Text(
                    text = "12 Services >>", // Placeholder for services count with arrow
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFF007AFF), // Blue color
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapCardPreview() {

    MapCard(
        locationText = "Lorem ipsum volutpat",
        searchRadius = "Search Radius 2KM",
        mapImagePainter = painterResource(R.drawable.geographical_map)
    )
}