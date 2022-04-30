package io.agapps.motchecker.home.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.agapps.motchecker.home.components.HomeHeader

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column {
        HomeHeader(modifier.fillMaxWidth().fillMaxHeight(fraction = 0.30f))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
