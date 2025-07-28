package com.kdbrian.templated.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdbrian.templated.App
import com.kdbrian.templated.domain.model.Vehicle
import com.kdbrian.templated.util.formatDateFromLong

@Composable
fun VehicleItem(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    onSelect: () -> Unit = {}
) {
    Surface(onSelect) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(vehicle.model)
                        }

                        append(" ")

                        withStyle(
                            SpanStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            )
                        ) {
                            append(vehicle.plateNumber)
                        }
                    },
                )

                Text(
                    text = buildAnnotatedString {
                        vehicle.lastServiced?.let {
                            withStyle(SpanStyle(fontSize = 16.sp)) {
                                append("last serviced ")
                                withStyle(SpanStyle(fontSize = 14.sp)) {
                                    append(formatDateFromLong(it))
                                }
                            }
                        }
                    },
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontSize = 16.sp)) {
                            append("last updated ")
                            withStyle(SpanStyle(fontSize = 14.sp)) {
                                append(formatDateFromLong(vehicle.lastUpdated))
                            }
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun VehicleItemPrev() {
    App {
        VehicleItem(
            vehicle = Vehicle(
                plateNumber = "ABC123",
                model = "Toyota Camry",
                mileage = 50000,
                fuelTankCapacity = 50.0,
                batteryCapacityKWh = 100.0,
                lastServiced = 169
            )
        )
    }
}