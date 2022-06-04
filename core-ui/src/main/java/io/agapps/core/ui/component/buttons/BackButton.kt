package io.agapps.core.ui.component.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.agapps.core.ui.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    tint: Color = Color.White
) {
    Icon(
        Icons.Filled.ArrowBack,
        stringResource(id = R.string.navigate_back),
        tint = tint,
        modifier = modifier
            .clickable {
                onBackClicked()
            }
            .padding(16.dp)
    )
}
