package io.agapps.feature.search.components

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
import io.agapps.core.ui.component.buttons.BackButton
import io.agapps.core.ui.theme.Black70
import io.agapps.core.ui.theme.Orange300
import io.agapps.core.ui.theme.Shapes
import io.agapps.core.ui.theme.numberPlateTextStyle
import io.agapps.feature.search.R

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun NumberPlateTextField(
    modifier: Modifier = Modifier,
    initialText: String = "",
    onTextChanged: ((String) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
) {
    var text by rememberSaveable { mutableStateOf(initialText) }
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
                    if (!newText.contains(" ")) {
                        text = newText
                        onTextChanged?.invoke(newText)
                    }
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
                BackButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    tint = Black70,
                    onBackClicked = {
                        onBackClicked()
                    }
                )
            }

            AnimatedVisibility(visible = text.isNotBlank(), enter = fadeIn(), exit = fadeOut(), modifier = Modifier.align(Alignment.CenterEnd)) {
                ClearButton {
                    text = ""
                    onTextChanged?.invoke("")
                    focusRequester.requestFocus()
                }
            }
        }

        LaunchedEffect(Unit) {
            if (initialText.isBlank()) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun ClearButton(
    modifier: Modifier = Modifier,
    onClearClicked: () -> Unit,
) {
    Icon(
        painter = painterResource(id = io.agapps.core.ui.R.drawable.ic_cross_filled),
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
