package com.kdbrian.tow.presentation.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kdbrian.tow.App
import com.kdbrian.tow.BuildConfig
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.presentation.nav.LandingRoute
import com.kdbrian.tow.presentation.ui.components.ActionCard
import com.kdbrian.tow.presentation.ui.components.ServiceCard
import kotlinx.coroutines.tasks.await
import org.koin.compose.koinInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

enum class Action { Update, Delete; }


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Profile(
    navHostController: NavHostController = rememberNavController()
) {
    val firebaseAuth = koinInject<FirebaseAuth>()
    val currentUser = firebaseAuth.currentUser
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val permissionState = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_IMAGES
        else
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var action by remember {
        mutableStateOf<Action?>(null)
    }

    var imageUri by remember { mutableStateOf<Uri?>(Uri.EMPTY) }


    LaunchedEffect(action) {
        when (action) {
            Action.Update -> {
                val snackbarResult = snackbarHostState.showSnackbar(
                    context.getString(R.string.updating_display_photo),
                    context.getString(R.string.confirm)
                )

                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    firebaseAuth.currentUser?.updateProfile(
                        com.google.firebase.auth.UserProfileChangeRequest.Builder()
                            .setPhotoUri(imageUri)
                            .build()
                    )?.await()
                    action = null
                }

            }

            Action.Delete -> {

                val snackbarResult = snackbarHostState.showSnackbar(
                    context.getString(R.string.confirm_to_delete_your_account_note),
                    context.getString(R.string.confirm)
                )

                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    firebaseAuth.currentUser?.delete()
                    navHostController.navigate(LandingRoute) {
                        popUpTo(LandingRoute) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }

            null -> Unit
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let {
            if (it != Uri.EMPTY) {
                imageUri = it
                action = Action.Update
            }
        }
    }


    Scaffold(
        snackbarHost = {
            androidx.compose.material3.SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            Surface(
                shadowElevation = 4.dp
            ){
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LocalAppColor.current,
                        navigationIconContentColor = Color.Black,
                        titleContentColor = Color.Black
                    ),
                    title = {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontSize = 20.sp,
                                fontFamily = LocalFontFamily.current,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
//                                .padding(16.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navHostController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                )
            }
        },
        containerColor = LocalAppColor.current
    ) { pd ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                ,
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = currentUser?.photoUrl ?: imageUri,
                    placeholder = painterResource(
                        R.drawable.current_premium
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            if (permissionState.status.isGranted) {
                                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            } else {
                                permissionState.launchPermissionRequest()
                            }
                        },
                    error = painterResource(R.drawable.current_premium),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            //count account a

            ActionCard(
                title = "${currentUser?.email ?: currentUser?.phoneNumber}\n",
//                subtitle = "Account age ${Date(System.currentTimeMillis() - currentUser)}",
                subtitle = buildString {
                    append("Account age ")
                    SetProfileData(currentUser)
                },
                trailingIcon = Icons.AutoMirrored.Rounded.Logout
            ) {
                firebaseAuth.signOut()
                navHostController.navigate(LandingRoute) {
                    popUpTo(LandingRoute) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }

            ActionCard(
                title = "Notification settings\n",
                subtitle = "get upto date notifications.",
                trailingIcon = null
            ) { }

            ServiceCard(
                title = "Premium",
                supportText = buildAnnotatedString {
                    append(stringResource(R.string.don_t_just_wait))
                    withStyle(SpanStyle()) {
                        append("priority service")
                    }
                    append("for ")
                    withStyle(
                        SpanStyle(
                            fontStyle = Italic,
                            fontWeight = FontWeight(40),
                            color = Color.Red
                        )
                    ) {
                        append("faster")
                    }
                    append(" tows and enjoy exclusive")

                    withStyle(SpanStyle()) {
                        append(" discounts")
                    }

                    append(" at our partner restaurants.")
                },
                backgroundImagePainter = painterResource(R.drawable.current_premium),
                modifier = Modifier.padding(top = 24.dp)
            ) {

            }


            TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
                action = Action.Delete
            }) {
                Text(
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color(0xFFFF0101)
                    ),
                    text = "Delete Account"
                )
            }

            Text(
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.LightGray
                ),
                text = BuildConfig.VERSION_NAME
            )
        }

    }
}

@Composable
private fun StringBuilder.SetProfileData(currentUser: FirebaseUser?) {
    currentUser?.metadata?.creationTimestamp?.let { creationTimestamp ->
        val currentTime = System.currentTimeMillis()
        val timeDifferenceMillis = currentTime - creationTimestamp

        val days = TimeUnit.MILLISECONDS.toDays(timeDifferenceMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis) % 24 // Remaining hours
        val minutes =
            TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60 // Remaining minutes
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis) % 60 // Remaining seconds
        val milliseconds = timeDifferenceMillis % 1000 // Remaining milliseconds

        val creationDate = Date(creationTimestamp)
        val dateFormat =
            SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault())
        val formattedCreationDate = dateFormat.format(creationDate)
        val ageDisplayString = when {
            days > 0 -> {
                "$days day${if (days != 1L) "s" else ""}, " +
                        "$hours hour${if (hours != 1L) "s" else ""}, " +
                        "$minutes minute${if (minutes != 1L) "s" else ""}"
            }

            hours > 0 -> {
                "$hours hour${if (hours != 1L) "s" else ""}, " +
                        "$minutes minute${if (minutes != 1L) "s" else ""}, " +
                        "$seconds second${if (seconds != 1L) "s" else ""}"
            }

            minutes > 0 -> {
                "$minutes minute${if (minutes != 1L) "s" else ""}, " +
                        "$seconds second${if (seconds != 1L) "s" else ""}"
            }

            seconds > 0 -> {
                "$seconds second${if (seconds != 1L) "s" else ""}, " +
                        "$milliseconds millisecond${if (milliseconds != 1L) "s" else ""}"
            }

            else -> {
                "$milliseconds millisecond${if (milliseconds != 1L) "s" else ""}"
            }
        }
        append(ageDisplayString)
    }
}

@Preview
@Composable
private fun ProfilePrev() {
    App {
        Profile()
    }
}