package io.agapps.motchecker.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.theme.Black70
import io.agapps.motchecker.ui.theme.Orange300
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.numberPlateTextStyle

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun NumberPlateTextField(
    modifier: Modifier = Modifier,
    onTextChanged: ((String) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
) {
    var text by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
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
                keyboardActions = KeyboardActions(onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .focusRequester(focusRequester)
            )

            if (onBackClicked != null) {
                BackButton(Modifier.align(Alignment.CenterStart)) {
                    onBackClicked()
                }
            }

            AnimatedVisibility(
                visible = text.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.CenterEnd)) {
                ClearButton {
                    text = ""
                    focusRequester.requestFocus()
                }
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
) {
    Icon(
        Icons.Filled.ArrowBack,
        stringResource(id = R.string.close_search),
        tint = Black70,
        modifier = modifier
            .clickable {
                onBackClicked()
            }
            .padding(16.dp)
    )
}

@Composable
private fun ClearButton(
    modifier: Modifier = Modifier,
    onClearClicked: () -> Unit,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_cross_filled),
        contentDescription = stringResource(id = R.string.clear_search),
        tint = Black70,
        modifier = modifier
            .clickable {
                onClearClicked()
            }
            .padding(16.dp)
        )
}

@Preview
@Composable
fun NumberPlateTextFieldPreview() {
    NumberPlateTextField {}
}
