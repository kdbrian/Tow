package com.kdbrian.templated.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.R
import com.kdbrian.templated.domain.model.FoodItemData
import com.kdbrian.templated.ui.components.EateriesCard
import com.kdbrian.templated.ui.components.EateriesSavedCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Eateries() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(pd)
                .padding(12.dp)
                .verticalScroll(scrollState)
        ) {

            EateriesCard(
                title = "Eateries",
                supportText = LoremIpsum(8).values.joinToString(),
                backgroundImagePainter = painterResource(R.drawable.fast_foods),
            ) { }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Saved",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = LocalFontFamily.current,
                    )
                )


                Icon(
                    imageVector = Icons.Rounded.Bookmark,
                    contentDescription = null
                )
            }

            Spacer(Modifier.padding(8.dp))
            FoodItemData.demoFoodItems.forEachIndexed { index, data ->
                EateriesSavedCard(foodItem = data)
            }
        }
    }
}

@Preview
@Composable
private fun EateriesPrev() {
    App {
        Eateries()
    }
}