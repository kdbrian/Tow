package com.kdbrian.tow

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowCompat
import com.kdbrian.tow.presentation.nav.MainNav
import com.kdbrian.tow.ui.theme.TemplatedTheme
import com.kdbrian.tow.presentation.ui.theme.appColor
import com.kdbrian.tow.ui.theme.manRope
import org.koin.dsl.koinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            enableEdgeToEdge()
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }

        koinApplication {
            setContent {
                App {
                    MainNav()
                }
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