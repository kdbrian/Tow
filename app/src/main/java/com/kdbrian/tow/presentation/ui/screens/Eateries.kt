package com.kdbrian.tow.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.R
import com.kdbrian.tow.domain.model.FoodItemData
import com.kdbrian.tow.presentation.ui.components.EateriesCard
import com.kdbrian.tow.presentation.ui.components.EateriesSavedCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Eateries(
    navHostController: NavHostController = rememberNavController()
) {

    Scaffold(
        containerColor = LocalAppColor.current,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalAppColor.current,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                ),
                title = {},
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
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