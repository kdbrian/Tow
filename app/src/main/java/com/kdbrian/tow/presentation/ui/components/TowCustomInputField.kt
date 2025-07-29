package com.kdbrian.tow.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.kdbrian.tow.App
import com.kdbrian.tow.LocalFontFamily

//


/**
 * A customizable input field composable with support for leading/trailing icons,
 * placeholder text, keyboard options, and state management.
 *
 * @param value The current text value of the input field.
// * @param onValueChange Callback that is triggered when the input text changes.
 * @param placeholderText The text to display when the input is empty.
 * @param leadingIcon Optional composable for the leading icon.
 * @param trailingIcon Optional composable for the trailing icon.
 * @param keyboardOptions Options for the software keyboard, such as keyboard type and IME action.
 * @param keyboardActions Actions to take when the user interacts with the keyboard (e.g., pressing "Done").
 * @param modifier Optional [Modifier] for this composable.
 * @param fieldShape The shape of the input field. Defaults to a rounded rectangle.
 * @param fieldColors The colors used for the input field's various states. Defaults to white background and black text/cursor.
 * @param isSingleLine When true, this text field will not allow multiple lines of text.
 */
@Composable
fun TowCustomInputField(
    modifier: Modifier = Modifier,
    value: TextFieldState = rememberTextFieldState(),
    placeholderText: String = LoremIpsum(2).values.joinToString(),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    fieldShape: RoundedCornerShape = RoundedCornerShape(28.dp),
    fieldColors: TextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        disabledContainerColor = Color.White,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Black.copy(alpha = 0.6f),
        cursorColor = Color.Black,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    ),
    enabled: Boolean = true,
    isSingleLine: Boolean = true
) {
    TextField(
        value = value.text.toString(),
        onValueChange = value::setTextAndPlaceCursorAtEnd,
        placeholder = {
            Text(
                text = placeholderText,
                fontFamily = LocalFontFamily.current,
                fontWeight = FontWeight.Light,
                color = fieldColors.unfocusedTextColor.copy(alpha = 0.6f)
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = TextStyle(fontFamily = LocalFontFamily.current),
        modifier = modifier
            .fillMaxWidth()
//            .height(50.dp)
        ,
        enabled = enabled,
        shape = fieldShape,
        colors = fieldColors,
        singleLine = isSingleLine
    )
}

@Preview
@Composable
private fun TowCustomInputFieldPrev() {

    App {
        TowCustomInputField()
    }
}