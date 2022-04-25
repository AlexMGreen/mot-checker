package io.agapps.motchecker.search.screens

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.agapps.motchecker.search.components.NumberPlateTextField

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    NumberPlateTextField(modifier.wrapContentHeight())
}
