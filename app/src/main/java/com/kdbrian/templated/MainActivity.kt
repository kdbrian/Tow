package com.kdbrian.templated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.kdbrian.templated.ui.screens.Landing
import com.kdbrian.templated.ui.theme.TemplatedTheme
import com.kdbrian.templated.ui.theme.appColor
import com.kdbrian.templated.ui.theme.manRope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {
                Landing()
            }

        }
    }
}


val LocalFontFamily = staticCompositionLocalOf {
    manRope
}
val LocalAppColor = staticCompositionLocalOf {
    appColor
}

@Composable
fun App(content: @Composable () -> Unit) {
    CompositionLocalProvider {
        TemplatedTheme {
            content()
        }
    }
}