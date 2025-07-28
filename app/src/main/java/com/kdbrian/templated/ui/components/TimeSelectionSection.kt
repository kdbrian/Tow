package com.kdbrian.templated.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Data class to represent a time option.
 *
 * @param label The text to display on the time button (e.g., "45 Mins", "1 hr").
 * @param value The actual value represented by the time option (e.g., 45, 60).
 */
data class TimeOption(
    val label: String,
    val value: Long
)

/**
 * Composable to display a section for selecting time options.
 * It includes a title and a row of selectable buttons.
 *
 * @param title The title for the time selection section.
 * @param timeOptions A list of [TimeOption] objects to display as selectable buttons.
 * @param selectedTime The currently selected [TimeOption].
 * @param onTimeSelected Callback to be invoked when a time option is selected.
 * @param modifier Optional [Modifier] for this composable.
 */
@Composable
fun TimeSelectionSection(
    title: String,
    timeOptions: List<TimeOption>,
    selectedTime: TimeOption?,
    onTimeSelected: (TimeOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding around the entire section
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround, // Distribute buttons evenly
            verticalAlignment = Alignment.CenterVertically
        ) {
            timeOptions.forEach { option ->
                val isSelected = option == selectedTime
                Button(
                    onClick = { onTimeSelected(option) },
                    modifier = Modifier
                        .weight(1f) // Each button takes equal weight
                        .height(50.dp)
                        .padding(horizontal = 4.dp), // Small padding between buttons
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFF00BCD4) else Color(0xFF8D8D8D), // Blue for selected, gray for unselected
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(25.dp) // Fully rounded corners
                ) {
                    Text(
                        text = option.label,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeSelectionSectionPreview() {
    val timeOptions = listOf(
        TimeOption("45 Mins", 45 * 60 * 100),
        TimeOption("50 Mins", 50 * 60 * 100),
        TimeOption("1 hr", 60 * 60 * 100)
    )

    var selectedTime by remember { mutableStateOf<TimeOption?>(timeOptions[0]) } // Default to 45 Mins selected

    TimeSelectionSection(
        title = "Select Time",
        timeOptions = timeOptions,
        selectedTime = selectedTime,
        onTimeSelected = { newTime -> selectedTime = newTime }
    )
}
