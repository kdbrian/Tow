package com.kdbrian.templated.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalAppColor
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R
import com.kdbrian.templated.ui.components.CustomArrowButton
import com.kdbrian.templated.ui.components.MapCard
import com.kdbrian.templated.ui.components.ServiceCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Landing() {
    Scaffold(
        containerColor = LocalAppColor.current
    ) { pd ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp),
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(192.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = LoremIpsum(2).values.joinToString(),
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontFamily = LocalFontFamily.current,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 48.sp
                            )
                        )

                        Text(
                            text = LoremIpsum(8).values.joinToString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = LocalFontFamily.current,
                                fontSize = 20.sp,
                                fontWeight = FontWeight(40)
                            )
                        )
                    }

                    Surface(
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
                                model = null,
                                placeholder = painterResource(R.drawable.cars),
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                            )
                        }
                    }
                }

            }

            item { Spacer(Modifier.padding(12.dp)) }

            item {
                ServiceCard {

                }
            }

            item { Spacer(Modifier.padding(12.dp)) }

            item {
                MapCard(
                    mapImagePainter = painterResource(R.drawable.geographical_map),
                    locationText = LoremIpsum(2).values.joinToString(),
                    searchRadius = 3.toString()
                )
            }

            item { Spacer(Modifier.padding(9.dp)) }

            item {

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CustomArrowButton(
                        text = "Request history",
                    ) {}

                    CustomArrowButton(text = "My Cars") {}
                }

            }

            item { Spacer(Modifier.padding(9.dp)) }

            item {
                ServiceCard(
                    title = "Eateries",
                    supportText = "Find the best eateries near you.",
                    backgroundImagePainter = painterResource(R.drawable.fast_foods),
                ) {}
            }


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