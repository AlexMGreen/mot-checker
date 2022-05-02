package io.agapps.motchecker.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.agapps.motchecker.home.components.NumberPlateTextField

@Suppress("UnusedPrivateMember")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.padding(8.dp)) {
        Box(modifier = modifier) {
            NumberPlateTextField(
                initialText = "",
                modifier = modifier.statusBarsPadding(),
                onTextChanged = {
                    viewModel.onRegistrationNumberEntered(it)
                },
                onCloseClicked = {
                    focusManager.clearFocus()
                    navController.popBackStack()
                }
            )
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(navController = rememberNavController())
}
