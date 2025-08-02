package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.presentation.ui.state.AuthViewModel
import com.kdbrian.tow.presentation.ui.theme.appSecondaryColor
import com.kdbrian.tow.util.Constants
import com.kdbrian.tow.util.Resource
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Composable function for the content of a bottom sheet, as depicted in the provided image.
 * It includes a title, supporting text, and two action buttons.
 *
// * @param onLoginClick Lambda to be invoked when the "Login" button is clicked.
// * @param onCreateAccountClick Lambda to be invoked when the "Create Account" button is clicked.
 * @param modifier Optional [Modifier] for this composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginAction(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit = {},
) {
    val pagerState = rememberPagerState { 4 }
    HorizontalPager(
        state = pagerState, userScrollEnabled = false
    ) {
        when (it) {
            0 -> Prompt(state = pagerState)
            1 -> SignInScreen(pagerState = pagerState, authViewModel = authViewModel)
            2 -> OtpVerificationScreen(
                pagerState = pagerState,
                authViewModel = authViewModel,
                onDismiss = onDismiss
            )
        }
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
//        Text(
//            text = "Get Started", style = MaterialTheme.typography.headlineSmall.copy(
//                fontFamily = LocalFontFamily.current,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            ), modifier = Modifier.padding(bottom = 8.dp)
//        )

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(8.dp)
        ){

            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = buildAnnotatedString {
                    append("By signing up you are agreeing to our terms and conditions.")
                    append("\nYou can read more at ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight(40),
                            color = Color(0xFF007AFF),
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("towconnect.com")
                    }
                    append(".\n")
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = LocalFontFamily.current, color = Color.Black.copy(alpha = 0.7f)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    state.scrollToPage(1)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
//                .padding(12.dp)
            ,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color(0xFF007AFF)
            ),
            shape = RoundedCornerShape(25.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // No shadow for transparent button
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = LocalFontFamily.current, fontWeight = FontWeight.Medium
                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInScreen(
    authViewModel: AuthViewModel, pagerState: PagerState
) {

    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val loginState by authViewModel.loginState.collectAsState()
    val phone = rememberTextFieldState()
    val snackbarHost = remember { SnackbarHostState() }

    LaunchedEffect(loginState) {
        if (loginState is Resource.Error) {
            snackbarHost.showSnackbar(loginState.message.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (pagerState.canScrollBackward) coroutineScope.launch {
                        pagerState.scrollToPage(pagerState.currentPage.dec())
                    }
                }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Sign In", style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 28.sp,
                    fontFamily = LocalFontFamily.current,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
        }

        // Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Phone Number", style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 20.sp,
                    fontFamily = LocalFontFamily.current,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ), modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = "A one time pin will be sent to your phone for verification",
                fontSize = 14.sp,
                color = Color(0xFF555555),
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // Phone Number Input
            TowCustomInputField(
                value = phone,
                leadingIcon = {
                    Text(
                        text = "+254 ", style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = LocalFontFamily.current,
                            fontWeight = FontWeight.Light,
                            fontSize = 24.sp
                        ), modifier = Modifier.padding(6.dp)
                    )
                },
                placeholderText = "type here",
                keyboardOptions = KeyboardOptions(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }),
                modifier = Modifier.padding(6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // OTP Button
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {

                Timber.d("loginState : $loginState")

                when (loginState) {
                    is Resource.Error -> {
                        Text(
                            text = loginState.message?.split(".")?.first().toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                fontFamily = LocalFontFamily.current
                            )
                        )
                    }

                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Nothing -> {
                        val input = "+254${phone.text}"
                        Timber.tag("matches")
                            .d("$input, ${Constants.kenyanPhoneRegex.matches(input)}")

                        Button(
                            onClick = {
//                                if (matches) {
                                authViewModel.startPhoneLogin(
                                    phone = input
                                )
//                                }
                            },
                            shape = RoundedCornerShape(30.dp), // More rounded corners
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            elevation = ButtonDefaults.buttonElevation(
                                //                            defaultElevation = 8.dp, // For shadow
                                pressedElevation = 4.dp
                            ),
                        ) {
                            Text(
                                text = "Request OTP",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontFamily = LocalFontFamily.current,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    is Resource.Success -> {
                        TextButton(
                            onClick = {
                                if (pagerState.canScrollForward) coroutineScope.launch {
                                    pagerState.scrollToPage(pagerState.currentPage.inc())
                                }
                            }, colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Green,
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                text = "I have the Otp",
                                fontFamily = LocalFontFamily.current,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    pagerState: PagerState,
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit = {}
) {

    // State to hold the OTP digits
    val verificationState by authViewModel.codeVerificationState.collectAsState()
    val code = rememberTextFieldState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(verificationState) {
        if (verificationState is Resource.Success) {
            onDismiss()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Light grey background
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (pagerState.canScrollBackward) coroutineScope.launch {
                    pagerState.scrollToPage(pagerState.currentPage.dec())
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "OTP verification",
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
                text = "Type the pin sent to your phone number.",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            // OTP Input Fields
            TowCustomInputField(
                value = code,
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                placeholderText = "type here",
            )


            Spacer(modifier = Modifier.height(50.dp)) // Space between input and button

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                when (verificationState) {
                    is Resource.Error -> {
                        Text(
                            text = verificationState.message.toString(),
                            color = Color.Red,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }

                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Nothing -> {
                        Button(
                            onClick = {
                                if (code.text.isNotEmpty()) {
                                    authViewModel.verifyCode(
                                        code.text.trim().toString()
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        ) {
                            Text(
                                text = "Verify",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    is Resource.Success -> Unit
                }
                // Verify Button
            }
        }
    }
}




