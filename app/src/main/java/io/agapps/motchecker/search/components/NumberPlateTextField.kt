package io.agapps.motchecker.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.ui.theme.NumberPlateYellow
import io.agapps.motchecker.ui.theme.Shapes

@Composable
fun NumberPlateTextField(modifier: Modifier = Modifier) {
    var text  = "ENTER REG"

    Card(
        shape = Shapes.medium,
        elevation = 8.dp,
        backgroundColor = NumberPlateYellow,
        modifier = Modifier
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = modifier.padding(8.dp)
        )
    }

}
