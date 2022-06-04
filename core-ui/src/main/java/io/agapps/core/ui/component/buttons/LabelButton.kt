package io.agapps.core.ui.component.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.core.ui.theme.Shapes

@Composable
fun LabelButton(
    modifier: Modifier = Modifier,
    text: String,
    onClicked: (() -> Unit),
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onClicked() },
        shape = Shapes.medium,
        elevation = 0.dp,
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
