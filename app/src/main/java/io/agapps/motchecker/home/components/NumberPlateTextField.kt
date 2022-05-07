package io.agapps.motchecker.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.theme.DarkGrey
import io.agapps.motchecker.ui.theme.Orange300
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.numberPlateTextStyle

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun NumberPlateTextField(
    modifier: Modifier = Modifier,
    initialText: String,
    onTextChanged: ((String) -> Unit)? = null,
    onNumberPlateClicked: (() -> Unit)? = null,
    onCloseClicked: (() -> Unit)? = null,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp),
        shape = Shapes.medium,
        backgroundColor = Orange300,
        elevation = 8.dp,
    ) {
        Box {
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                    onTextChanged?.invoke(newText)
                },
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
                keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                enabled = onNumberPlateClicked == null,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        text = if (it.isFocused) "" else initialText
                    }
                    .clickable { onNumberPlateClicked?.invoke() }
                    .focusRequester(focusRequester)
            )

            if (onCloseClicked != null) {
                CloseButton(modifier.align(Alignment.CenterEnd)) {
                    onCloseClicked()
                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun CloseButton(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
) {
    Icon(
        Icons.Filled.Close,
        stringResource(id = R.string.close_search),
        tint = DarkGrey,
        modifier = modifier
            .padding(end = 16.dp)
            .clickable {
                onCloseClicked()
            }
    )
}

@Preview
@Composable
fun NumberPlateTextFieldPreview() {
    NumberPlateTextField(initialText = "ENTER REG")
}
