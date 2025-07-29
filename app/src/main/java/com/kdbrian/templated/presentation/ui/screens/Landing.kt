package com.kdbrian.templated.presentation.ui.screens

import android.credentials.GetCredentialException
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalAppColor
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R
import com.kdbrian.templated.presentation.nav.EateriesRoute
import com.kdbrian.templated.presentation.nav.MyVehiclesRoute
import com.kdbrian.templated.presentation.nav.ProfileRoute
import com.kdbrian.templated.presentation.nav.RequestHistoryRoute
import com.kdbrian.templated.presentation.nav.RequestServiceRoute
import com.kdbrian.templated.ui.components.CustomArrowButton
import com.kdbrian.templated.ui.components.LoginAction
import com.kdbrian.templated.ui.components.MapCard
import com.kdbrian.templated.ui.components.ServiceCard
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Landing(
    navHostController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val firebaseAuth = koinInject<FirebaseAuth>()
    var createAccountVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    val credentialManager = remember { CredentialManager.create(context) }
    val requestAuth: () -> Unit = {
        coroutineScope.launch {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            try {
                // 1. Create a GetCredentialRequest for Google ID tokens
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // 2. Launch the Credential Manager flow
                val response = credentialManager.getCredential(context, request)

                when (val credential = response.credential) {
                    // Passkey credential
                    is PublicKeyCredential -> {
                        val responseJson = credential.authenticationResponseJson
                        // In a real app, you'd send this to your backend for passkey authentication
                        snackbarHostState.showSnackbar("Passkey credential received. Backend integration needed.")
                    }
                    // Password credential
                    is PasswordCredential -> {
                        // Send ID and password to your server to validate and authenticate.
                        val username = credential.id
                        val password = credential.password
                        snackbarHostState.showSnackbar("Password credential received. Handle username/password login.")
                    }
                    // GoogleIdToken credential (as CustomCredential)
                    is CustomCredential -> {
                        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            try {
                                val googleIdTokenCredential = GoogleIdTokenCredential
                                    .createFrom(credential.data)

                                val googleIdToken = googleIdTokenCredential.idToken

                                val email = googleIdTokenCredential.id
                                val displayName = googleIdTokenCredential.displayName


                                Timber.d("for $email $displayName")
                            } catch (e: GoogleIdTokenParsingException) {
                                snackbarHostState.showSnackbar("Received an invalid Google ID token response: ${e.message}")
                            }
                        } else {
                            // Catch any unrecognized custom credential type here.
                            snackbarHostState.showSnackbar("Unexpected type of custom credential: ${credential.type}")
                        }
                    }

                    else -> {
                        snackbarHostState.showSnackbar("Unknown credential type returned.")
                    }
                }
            } catch (e: GetCredentialException) {
                snackbarHostState.showSnackbar("Credential Manager error: ${e.message}")
            } catch (e: Exception) {
                Timber.tag("Exception").d(e.message.toString())
                snackbarHostState.showSnackbar("An unexpected error occurred: ${e.message}")
//                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = LocalAppColor.current
    ) { pd ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Good day",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontFamily = LocalFontFamily.current,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 48.sp
                        )
                    )

                    Text(
                        text = "Service, drinks & food at your comfort.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = LocalFontFamily.current,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(40)
                        )
                    )
                }

                Surface(
                    onClick = {
                        if (firebaseAuth.currentUser == null) {
                            createAccountVisible = true
                        } else {
                            navHostController.navigate(ProfileRoute)
                        }
                    },
                    shape = CircleShape,
                    border = BorderStroke(
                        width = 4.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFF3D9B1),
                                Color(0xFFF27059),
                            )
                        )
                    ),
                    shadowElevation = (-3).dp
                ) {
                    Box(
                        modifier = Modifier.size(50.dp)
                    ) {
                        AsyncImage(
                            model = firebaseAuth.currentUser?.photoUrl,
                            placeholder = painterResource(R.drawable.cars),
                            modifier = Modifier.fillMaxSize(),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                        )
                    }
                }
            }

            ServiceCard(
                title = "Request Service",
                backgroundImagePainter = painterResource(R.drawable.tow_truck)
            ) {
                navHostController.navigate(RequestServiceRoute)
            }

            MapCard(
                mapImagePainter = painterResource(R.drawable.geographical_map),
                locationText = LoremIpsum(2).values.joinToString(),
                searchRadius = 3.toString()
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomArrowButton(
                    text = "Request history",
                ) { navHostController.navigate(RequestHistoryRoute) }

                CustomArrowButton(text = "My Cars") { navHostController.navigate(MyVehiclesRoute) }
            }

            ServiceCard(
                title = "Eateries",
                supportText = "Find the best eateries near you.",
                backgroundImagePainter = painterResource(R.drawable.fast_foods),
                onClick = { navHostController.navigate(EateriesRoute) }
            )

        }
    }

    if (createAccountVisible) {
        ModalBottomSheet(
            onDismissRequest = { createAccountVisible = false },
            sheetState = bottomSheetState
        ) {
            LoginAction(
                onLoginClick = {
                    createAccountVisible = false
                    requestAuth()
                },
                onCreateAccountClick = {
                    createAccountVisible = false
                    requestAuth()
                }
            )


        }
    }


}

@Preview
@Composable
private fun LandingPrev() {
    App {
        Landing()
    }
}