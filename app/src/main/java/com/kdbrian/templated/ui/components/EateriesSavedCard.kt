package com.kdbrian.templated.ui.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R
import com.kdbrian.templated.domain.model.FoodItemData

/**
 * A composable card to display a food item with its image, title, subtitle, and price.
 *
 * @param foodItem The [FoodItemData] containing all the information for the food item.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun EateriesSavedCard(
    modifier: Modifier = Modifier,
    foodItem: FoodItemData
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f) // Take available space
            ) {
                AsyncImage(
                    model = foodItem.imageUrl,
                    contentDescription = foodItem.title,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.eatery_place),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = foodItem.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = LocalFontFamily.current,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = foodItem.subtitle,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = LocalFontFamily.current,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            // Right Section: Price
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End // Align price to the end
            ) {
                Text(
                    text = foodItem.currency,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.Gray,
                        fontFamily = LocalFontFamily.current,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = foodItem.price,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = LocalFontFamily.current,
                        color = Color.Black,
                        fontSize = 18.sp // Match title font size for price prominence
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FoodItemCardPreview() {
    val sampleFoodItem = FoodItemData(
        imageUrl = "https://placehold.co/100x100/FF0000/FFFFFF/png?text=KFC", // Placeholder for KFC drumsticks
        title = "Tripple Drumsticks",
        subtitle = "Served by KFC",
        price = "3,000",
        currency = "Kes"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        repeat(10) {
            EateriesSavedCard(foodItem = sampleFoodItem)
        }
    }
}
