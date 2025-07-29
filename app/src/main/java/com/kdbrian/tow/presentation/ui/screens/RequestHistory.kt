package com.kdbrian.tow.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalAppColor
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.domain.model.RequestHistoryData
import com.kdbrian.tow.presentation.nav.RequestDetailsRoute
import com.kdbrian.tow.presentation.ui.components.RequestHistoryItem
import com.kdbrian.tow.ui.components.TowCustomInputField

//RequestHistory
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestHistory(
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
                title = {
                    Column {
                        Text(
                            text = "Request History",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = LocalFontFamily.current
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { pd ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd)
                .padding(16.dp)
        ) {

            TowCustomInputField(
                placeholderText = "Search",
                modifier = Modifier.padding(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                },
                fieldShape = RoundedCornerShape(32.dp)
            )

            RequestHistoryData.demos.forEachIndexed { index, data ->
                key(index) {
                    RequestHistoryItem(
                        requestHistory = data
                    ) {
                        navHostController.navigate(RequestDetailsRoute(data.id))
                    }
                }
            }
        }
    }


}

@Preview
@Composable
private fun RequestHistoryPrev() {
    App { RequestHistory() }
}