package io.agapps.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.core.ui.component.buttons.BackButton
import io.agapps.core.ui.theme.Black70
import io.agapps.core.ui.theme.Orange300
import io.agapps.core.ui.theme.Shapes
import io.agapps.core.ui.theme.numberPlateTextStyle

@Composable
fun NumberPlateText(
    text: String,
    modifier: Modifier = Modifier,
    onNumberPlateClicked: (() -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = Shapes.medium,
        backgroundColor = Orange300,
        elevation = 8.dp,
    ) {
        Box {
            Text(
                text = text,
                style = numberPlateTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNumberPlateClicked?.invoke() }
                    .padding(16.dp)
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
        }

    }
}

@Preview
@Composable
fun NumberPlateTextPreview() {
    NumberPlateText("ENTER REG")
}
