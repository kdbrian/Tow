package com.kdbrian.tow.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalFontFamily

@Composable
fun AppHint(
    modifier: Modifier = Modifier,
    text: AnnotatedString = buildAnnotatedString {
        append("Welcome to Tow")
    },
    onSelect: () -> Unit = {}
) {
    
    Surface(
        shape = RoundedCornerShape(12.dp),
        onClick = onSelect,
        color = Color.LightGray.copy(alpha = 0.25f),
        contentColor = Color.White
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {

            Icon(
                imageVector = Icons.Rounded.Lightbulb,
                tint = Color.Yellow,
                contentDescription = null
            )

            Spacer(Modifier.padding(4.dp))

            Text(
                text = text,
                modifier = modifier.padding(4.dp),
                style = MaterialTheme.typography.labelLarge
                    .copy(
                        fontFamily = LocalFontFamily.current,
                        fontWeight = FontWeight(45)
                    ),
            )

        }

    }


}


@Preview
@Composable
private fun AppHintPrev() {
    App {
        AppHint()
    }
}
