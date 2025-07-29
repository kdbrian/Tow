package com.kdbrian.tow.presentation.ui.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.kdbrian.tow.App
import com.kdbrian.tow.BuildConfig
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.ui.components.ActionCard
import com.kdbrian.tow.ui.components.ServiceCard
import org.koin.compose.koinInject

@Composable
fun Profile(
    navHostController: NavHostController = rememberNavController()
) {
    val firebaseAuth = koinInject<FirebaseAuth>()


    Scaffold(
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

            Text(
                text = "Profile",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 20.sp,
                    fontFamily = LocalFontFamily.current,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(50.dp)
            )

            Box {
                AsyncImage(
                    model = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .padding(4.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }

            ActionCard(
                title = "${firebaseAuth.currentUser?.email}\n",
                subtitle = "Account age 1yr 2mo",
                trailingIcon = Icons.AutoMirrored.Rounded.Logout
            ) { }

            ActionCard(
                title = "Notification settings\n",
                subtitle = "get upto date notifications.",
                trailingIcon = null
            ) { }

            ServiceCard(
                title = "Premium",
                backgroundImagePainter = painterResource(R.drawable.current_premium),
                modifier = Modifier.padding(top = 24.dp)
            ) {

            }


            TextButton(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {}) {
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

@Preview
@Composable
private fun ProfilePrev() {
    App {
        Profile()
    }
}