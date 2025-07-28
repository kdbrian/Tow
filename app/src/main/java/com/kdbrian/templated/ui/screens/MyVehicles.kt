package com.kdbrian.templated.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalAppColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVehicles(

) {

    Scaffold(
        containerColor = LocalAppColor.current,

                topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("My Vehicles")
                            }
                            append("\n")
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(60)
                                )
                            ) {
                                append("saved 30 vehicles.")
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        Box(
            modifier = Modifier.padding(pd)
        )
    }

}


@Preview
@Composable
private fun MyVehiclesPrev() {
    App {
        MyVehicles()
    }
}
