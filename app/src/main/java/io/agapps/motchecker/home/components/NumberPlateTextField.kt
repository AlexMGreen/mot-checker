package io.agapps.motchecker.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.ui.theme.Orange300
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.numberPlateTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NumberPlateTextField(
    modifier: Modifier = Modifier,
    initialText: String,
    onNumberPlateClicked: (() -> Unit)? = null,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    val focusRequester = remember { FocusRequester() }

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp),
        shape = Shapes.medium,
        backgroundColor = Orange300,
        elevation = 8.dp,
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = numberPlateTextStyle,
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
            enabled = onNumberPlateClicked == null,
            modifier = modifier
                .onFocusChanged {
                    text = if (it.isFocused) "" else initialText

                }
                .clickable { onNumberPlateClicked?.invoke() }
                .focusRequester(focusRequester)
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Preview
@Composable
fun NumberPlateTextFieldPreview() {
    NumberPlateTextField(initialText = "ENTER REG")
}
