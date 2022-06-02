package io.agapps.motchecker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.core.ui.theme.numberPlateTextStyle
import io.agapps.motchecker.ui.theme.Orange300
import io.agapps.motchecker.ui.theme.Shapes


@Composable
fun NumberPlateText(
    text: String,
    modifier: Modifier = Modifier,
    onNumberPlateClicked: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = Shapes.medium,
        backgroundColor = Orange300,
        elevation = 8.dp,
    ) {
        Text(
            text = text,
            style = numberPlateTextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNumberPlateClicked?.invoke() }
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun NumberPlateTextPreview() {
    NumberPlateText("ENTER REG")
}

