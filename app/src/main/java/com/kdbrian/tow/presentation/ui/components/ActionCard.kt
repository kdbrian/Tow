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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * A composable card to display an action item with a title, subtitle,
 * and a clickable trailing icon.
 *
 * @param title The main title text, as an [AnnotatedString] for potential rich text.
 * @param subtitle The supporting text or description (e.g., "Account age 1yr 2mo").
 * @param trailingIcon The icon to display at the end of the card.
 * @param onClick The action to perform when the card (or the trailing icon) is clicked.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun ActionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
//            .height(90.dp)
        ,
        onClick = onClick,
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
        contentColor = Color.Black,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }

            trailingIcon?.let {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "Action",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionCardPreview() {
    // Example usage of the ActionCard with direct parameters
    val sampleTitle = "Joedoe@outlook.com"
    val sampleSubtitle = "Account age 1yr 2mo"
    val sampleTrailingIcon = Icons.Default.ArrowForward // Using ArrowForward as a placeholder

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ActionCard(
            title = sampleTitle,
            subtitle = sampleSubtitle,
            trailingIcon = sampleTrailingIcon,
            onClick = { /* Handle click action */ }
        )
    }
}
