package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalFontFamily
import com.kdbrian.tow.domain.model.VehicleDto


@Composable
fun VehicleModelItem(
    modifier: Modifier = Modifier,
    vehicleDto: VehicleDto,
    onSaved: () -> Unit = {}
) {

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.padding(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontFamily = LocalFontFamily.current,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(vehicleDto.vehicle.model)
                    }

                    withStyle(
                        SpanStyle(
                            fontFamily = LocalFontFamily.current,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append(vehicleDto.vehicle.fuelTankCapacity.toString() + "ltrs")
                    }
                }
            )

            IconButton(onClick = onSaved) {

            }
        }

    }


}

@Preview
@Composable
private fun VehicleModelItemPrev() {
    App {
//        VehicleModelItem()
    }
}