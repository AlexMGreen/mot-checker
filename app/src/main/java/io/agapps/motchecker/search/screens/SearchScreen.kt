package io.agapps.motchecker.search.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.agapps.motchecker.R
import io.agapps.motchecker.search.components.NumberPlateTextField

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column {
        NumberPlateTextField(
            stringResource(id = R.string.enter_reg),
            modifier.wrapContentHeight().fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
