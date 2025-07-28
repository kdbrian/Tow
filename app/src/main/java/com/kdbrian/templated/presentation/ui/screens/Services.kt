package com.kdbrian.templated.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.App
import com.kdbrian.templated.LocalAppColor
import com.kdbrian.templated.LocalFontFamily
import com.kdbrian.templated.domain.model.ServiceItem
import com.kdbrian.templated.ui.components.TowCustomInputField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Services(

) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        containerColor = LocalAppColor.current,

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = LocalFontFamily.current
                                    )
                                ) {
                                    append("Request Service")
                                }
                                withStyle(
                                    SpanStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Light,
                                        fontFamily = LocalFontFamily.current
                                    )
                                ) {
                                    append("Explore offered services")
                                }
                            },
                        )

                    }
                },
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
        LazyColumn(
            modifier = Modifier
                .padding(pd)
                .padding(12.dp)
        ) {

            item {
                TowCustomInputField(
                    fieldShape = RoundedCornerShape(32.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                    },
//                    placeholderText = "type to search",
                    placeholderText = "",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        keyboardController?.hide()
                    })
                )
            }

            itemsIndexed(ServiceItem.demoServices) { index, service ->
                Surface {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = service.name, style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = service.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }
                }

            }

        }
    }

}

@Preview
@Composable
private fun ServicesPrev() {
    App {
        Services()
    }
}