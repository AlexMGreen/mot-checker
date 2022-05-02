package io.agapps.motchecker.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.agapps.motchecker.home.components.NumberPlateTextField

@Suppress("UnusedPrivateMember")
@Composable
fun SearchScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    // TODO: Account for status bar height
    Column(modifier = modifier.padding(vertical = 32.dp, horizontal = 8.dp)) {
        NumberPlateTextField(initialText = "")
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}
