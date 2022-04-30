package io.agapps.motchecker.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.ui.theme.Black30
import io.agapps.motchecker.ui.theme.Orange300
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.numberPlateTextStyle

@Composable
fun NumberPlateTextField(initialText: String, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    var textColor by rememberSaveable { mutableStateOf(Black30.toArgb()) }

    Card(
        shape = Shapes.medium,
        elevation = 8.dp,
        backgroundColor = Orange300,
        modifier = Modifier
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = numberPlateTextStyle.copy(
                textAlign = TextAlign.Center,
                color = Color(textColor)
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Search
            ),
            modifier = modifier
                .onFocusChanged {
                    text = if (it.isFocused) "" else initialText
                    textColor = if (it.isFocused) Color.Black.toArgb() else Black30.toArgb()

                }
        )
    }

}

@Preview
@Composable
fun NumberPlateTextFieldPreview() {
    NumberPlateTextField("ENTER REG")
}
