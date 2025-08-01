package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.tow.LocalFontFamily


/**
 * A reusable button composable with text and an optional trailing arrow icon,
 * designed to match the "My Cars" button style.
 *
 * @param text The text to display on the button.
 * @param onClick The action to perform when the button is clicked.
 * @param modifier Optional [Modifier] for this composable.
 * @param showArrowIcon Whether to display the trailing arrow icon. Defaults to true.
 * @param buttonColors The colors for the button. Defaults to a white background with black text.
 * @param buttonShape The shape of the button. Defaults to a rounded rectangle.
 */
@Composable
fun CustomArrowButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
    showArrowIcon: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.White,
        contentColor = Color.Black
    ),
    buttonShape: RoundedCornerShape = RoundedCornerShape(16.dp),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp), // Consistent height
        colors = buttonColors,
        shape = buttonShape,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp) // Slight shadow
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = LocalFontFamily.current,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp // Adjust font size as needed
                )
            )
            if (showArrowIcon) {
                Icon(
                    imageVector = icon,
                    contentDescription = null, // Decorative icon
                    tint = buttonColors.contentColor // Use content color for the icon
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomArrowButtonPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Default "My Cars" style button
        CustomArrowButton(
            text = "My Cars",
            onClick = { /* Handle click */ }
        )

        // Custom colored button
        CustomArrowButton(
            text = "View Details",
            onClick = { /* Handle click */ },
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1ABC9C), // Emerald green
                contentColor = Color.White
            ),
            buttonShape = RoundedCornerShape(12.dp)
        )

        // Button without arrow
        CustomArrowButton(
            text = "Submit Form",
            onClick = { /* Handle click */ },
            showArrowIcon = false,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2980B9), // Peter River blue
                contentColor = Color.White
            )
        )
    }
}
