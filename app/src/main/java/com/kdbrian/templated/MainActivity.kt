package com.kdbrian.templated

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kdbrian.templated.ui.theme.TemplatedTheme
import com.kdbrian.templated.ui.theme.manRope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App {  }
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