package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kdbrian.tow.domain.model.RestaurantData


/**
 * A composable card to display restaurant information with its image, name,
 * nearest distance, and number of branches.
 *
 * @param restaurant The [com.kdbrian.tow.domain.model.RestaurantData] containing all the information for the restaurant.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun RestaurantCard(
    restaurant: RestaurantData,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp), // Fixed height similar to FoodItemCard
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Take available space
            ) {
                AsyncImage(
                    model = restaurant.imageUrl,
                    contentDescription = restaurant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Name, Nearest Distance, and Branches Count
                Column {
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = restaurant.nearestDistance,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = restaurant.branchesCount,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    )
                }
            }
            // No right section for price in this card, as per image
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantCardPreview() {
    val sampleRestaurant = RestaurantData(
        imageUrl = "https://placehold.co/100x100/FF0000/FFFFFF/png?text=KFC", // Placeholder for KFC image
        name = "Kentucky Fried Chicken(KFC)",
        nearestDistance = "Nearest 21KM",
        branchesCount = "Over 30 branches"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RestaurantCard(restaurant = sampleRestaurant)
    }
}
