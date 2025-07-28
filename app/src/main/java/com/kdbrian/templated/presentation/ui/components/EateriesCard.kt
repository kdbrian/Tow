package com.kdbrian.templated.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R

/**
 * A modular and reusable card component for displaying an "Eateries" section.
 * It includes a background image, a title, supporting text, and a clickable action.
 * The text content is aligned to the center.
 *
 * @param title The main title text for the card (e.g., "Eateries").
 * @param supportText The supporting text displayed below the title.
 * @param backgroundImagePainter The painter for the background image.
 * @param onClick The action to perform when the card is clicked.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun EateriesCard(
    modifier: Modifier = Modifier,
    title: String,
    supportText: String,
    backgroundImagePainter: Painter,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(194.dp)
            .padding(4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Background Image
            Image(
                painter = backgroundImagePainter,
                contentDescription = null, // Decorative image
                contentScale = ContentScale.Crop, // Crop to fill the bounds
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.30f),
                                Color.Black.copy(alpha = 0.85f),
                            ),
                        )
                    )
            )

            // Text Content (Title and Support Text)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center, // Align content to the center vertically
                horizontalAlignment = Alignment.CenterHorizontally // Align content to the center horizontally
            ) {
                Text(
                    text = title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        fontFamily = LocalFontFamily.current
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = supportText,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(40),//TODO: update with some default value used in the entire app
                        fontFamily = LocalFontFamily.current,
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.widthIn(max = 280.dp)
                )
            }
        }
    }
}

// Preview Composable (for demonstration purposes)
@Preview(showBackground = true)
@Composable
fun EateriesCardPreview() {

    EateriesCard(
        title = "Eateries",
        supportText = "Lorem ipsum consectetur ultricies libero justo velit etiam morbi malesuada et lacus.",
        backgroundImagePainter = painterResource(R.drawable.fast_foods), // Replace with your actual image painter
        onClick = { /* Handle card click */ }
    )
}