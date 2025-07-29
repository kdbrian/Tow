package com.kdbrian.tow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.presentation.ui.theme.appSecondaryColor


/**
 * Composable function for the content of a bottom sheet, as depicted in the provided image.
 * It includes a title, supporting text, and two action buttons.
 *
 * @param onLoginClick Lambda to be invoked when the "Login" button is clicked.
 * @param onCreateAccountClick Lambda to be invoked when the "Create Account" button is clicked.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun LoginAction(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Title
        Text(
            text = "Get Started",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = LocalFontFamily.current,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Lorem ipsum mauris sem tempus quis elementum vulputate in elementum eu cursus.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = LocalFontFamily.current,
                color = Color.Black.copy(alpha = 0.7f)
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCreateAccountClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF007AFF)
                ),
                shape = RoundedCornerShape(25.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // No shadow for transparent button
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = LocalFontFamily.current,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .weight(1f) // Take equal weight
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = appSecondaryColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = LocalFontFamily.current,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginActionPreview() {
    LoginAction(
        onLoginClick = { },
        onCreateAccountClick = { }
    )
}