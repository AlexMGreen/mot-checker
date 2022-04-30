package io.agapps.motchecker.search.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.agapps.motchecker.search.components.SearchHeader

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column {
        SearchHeader(modifier.fillMaxWidth().fillMaxHeight(fraction = 0.30f))
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
