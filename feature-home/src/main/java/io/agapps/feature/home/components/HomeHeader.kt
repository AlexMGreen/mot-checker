package io.agapps.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.feature.home.R
import io.agapps.motchecker.ui.components.NumberPlateText
import io.agapps.motchecker.ui.theme.Black30

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    onNumberPlateClicked: (() -> Unit)? = null,
) {
    Box(modifier = modifier) {
        Image(
            painterResource(id = R.drawable.home_header_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Black30)
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(id = R.string.search_vehicle),
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            NumberPlateText(
                stringResource(id = R.string.enter_reg),
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                onNumberPlateClicked = onNumberPlateClicked
                )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun HomeHeaderPreview() {
    HomeHeader(
        Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}