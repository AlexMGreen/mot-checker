package io.agapps.motchecker.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.R

@Composable
fun AppBottomBar(
    modifier: Modifier,
    endIcon: @Composable (() -> Unit)? = null,
) {
    BottomAppBar(
        modifier = modifier,
        cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        elevation = 0.dp
    ) {
        IconButton(
            onClick = {
                // TODO: Show menu bottomsheet
            }) {
            Icon(Icons.Filled.Menu, contentDescription = stringResource(id = R.string.menu))
        }
        Spacer(Modifier.weight(1f, true))
        endIcon?.invoke()
    }
}
