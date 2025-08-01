package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.presentation.ui.state.AuthViewModel
import com.kdbrian.tow.presentation.ui.theme.appSecondaryColor
import kotlinx.coroutines.launch


/**
 * Composable function for the content of a bottom sheet, as depicted in the provided image.
 * It includes a title, supporting text, and two action buttons.
 *
// * @param onLoginClick Lambda to be invoked when the "Login" button is clicked.
// * @param onCreateAccountClick Lambda to be invoked when the "Create Account" button is clicked.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun LoginAction(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
) {
    val pagerState = rememberPagerState { 4 }
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false
    ) {
        if (it == 0)
            Prompt(state = pagerState)
        else if (it == 1)
            SignInScreen()
    }


}

@Composable
private fun Prompt(
    state: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
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
                onClick = { },
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
                onClick = {
                    coroutineScope.launch {
                        state.scrollToPage(1)
                    }
                },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun SignInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back button click */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Sign In",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Phone Number",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "A one time pin will be sent to your phone for verification",
                fontSize = 14.sp,
                color = Color(0xFF555555),
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // Phone Number Input
            TowCustomInputField(
                placeholderText = "type here",
                modifier = Modifier.padding(6.dp)
            )

            // OTP Button
            Button(
                onClick = { /* Handle OTP button click */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f) // 80% width
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(30.dp), // More rounded corners
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp, // For shadow
                    pressedElevation = 4.dp
                ),
                contentPadding = PaddingValues(vertical = 18.dp)
            ) {
                Text(
                    text = "I have the OTP",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

