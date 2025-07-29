package com.kdbrian.tow.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R

@Composable
fun ServiceCard(
    modifier: Modifier = Modifier,
    title: String = LoremIpsum(2).values.joinToString(),
    supportText: String = LoremIpsum(12).values.joinToString(),
    backgroundImagePainter: Painter = painterResource(R.drawable.cars),
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp,
        contentColor = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = backgroundImagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.40f),
                                Color.Black
                            ),
                            start = Offset(12f, 0f),
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                        .copy(
                            fontFamily = LocalFontFamily.current
                        )
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = supportText,
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            fontFamily = LocalFontFamily.current,
                            textAlign = TextAlign.End,
                        )
                )
            }
        }
    }
}


@Preview
@Composable
private fun ServiceCardPrev() {
    App {
        ServiceCard(
            title = "Request Service",
            supportText = LoremIpsum(12).values.joinToString(),
            backgroundImagePainter = painterResource(R.drawable.cars),
            onClick = { }
        )

    }
}