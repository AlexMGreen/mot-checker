package io.agapps.feature.home.screens

import android.annotation.SuppressLint
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.agapps.core.ui.component.AppBottomBar
import io.agapps.core.ui.theme.MOTCheckerTheme
import io.agapps.core.ui.theme.SurfaceGrey
import io.agapps.feature.camerasearch.component.CameraSearchCard
import io.agapps.feature.home.R
import io.agapps.feature.home.components.HomeHeader

@Composable
fun HomeRoute(
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScreen(navigateToSearch, modifier)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToSearch() }) {
                Icon(Icons.Outlined.Search, stringResource(id = io.agapps.core.ui.R.string.search), tint = SurfaceGrey)
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            AppBottomBar(modifier = Modifier) {
                IconButton(
                    onClick = {
                        // TODO: Navigate to saved vehicles
                    }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = stringResource(id = R.string.saved_vehicles))
                }
            }
        }
    ) { paddingValues ->
        Column {
            HomeHeader(
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.30f)
            ) {
                navigateToSearch()
            }

            // TODO: Permission request handling on click
            CameraSearchCard()
        }
    }
}

@Preview(heightDp = 800, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    MOTCheckerTheme {
        HomeScreen({})
    }
}
