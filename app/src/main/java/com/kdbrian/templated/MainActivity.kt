package com.kdbrian.templated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.kdbrian.templated.ui.components.RestaurantCard
import com.kdbrian.templated.domain.model.RestaurantData
import com.kdbrian.templated.ui.theme.TemplatedTheme
import com.kdbrian.templated.ui.theme.manRope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {
                RestaurantCard(
                    restaurant = RestaurantData(
                        imageUrl = "",
                        name = "Kentucky Fried Chicken(KFC)",
                        nearestDistance = "Nearest 21KM",
                        branchesCount = "Over 30 branches"
                    )
                )
            }

        }
    }
}


val LocalFontFamily = staticCompositionLocalOf {
    manRope
}

@Composable
fun App(content: @Composable () -> Unit) {
    CompositionLocalProvider {
        TemplatedTheme {
            content()
        }
    }
}