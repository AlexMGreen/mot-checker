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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.core.ui.component.NumberPlateText
import io.agapps.core.ui.theme.Black30
import io.agapps.core.ui.theme.LightGrey
import io.agapps.feature.home.R

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    fadeOnCollapseAlpha: Float,
    onNumberPlateClicked: (() -> Unit)? = null,
) {
    Box(modifier = modifier.background(LightGrey)) {
        Image(
            painterResource(id = R.drawable.home_header_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .alpha(fadeOnCollapseAlpha)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Black30)
                .alpha(fadeOnCollapseAlpha)
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
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .alpha(fadeOnCollapseAlpha)
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
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        fadeOnCollapseAlpha = 1f
    )
}
