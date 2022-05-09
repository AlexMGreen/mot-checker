package io.agapps.motchecker.home.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.components.IconLabel
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.White10
import io.agapps.motchecker.ui.theme.White50

private const val ImageGradientEnd = 600f

@Composable
fun CameraSearchCard(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onClick: () -> Unit
) {

    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")

    val fadeOutOnExpandAlpha by transition.animateFloat({ tween(durationMillis = 500) }, label = "") { isExpanded -> if (isExpanded) 0f else 1f }
    val fadeInOnExpandAlpha by transition.animateFloat({ tween(durationMillis = 500) }, label = "") { isExpanded -> if (isExpanded) 1f else 0f }
    val cardHeightRatio by transition.animateFloat({ tween(durationMillis = 500) }, label = "") { isExpanded -> if (isExpanded) 0.5f else 0.25f }

    Card(
        elevation = 8.dp,
        modifier = modifier
            .padding(16.dp)
            .clickable {
                transitionState.targetState = !transitionState.currentState
            }
            .fillMaxWidth()
            .fillMaxHeight(cardHeightRatio)
            .border(width = 1.dp, color = White50, shape = Shapes.medium)

    ) {
        CollapsedCardContents(fadeOutOnExpandAlpha)
    }
}

@Composable
private fun CollapsedCardContents(
    alpha: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.alpha(alpha)) {
        Image(
            painterResource(id = R.drawable.camera_search_background),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black), 0f, ImageGradientEnd))
        )

        IconLabel(
            label = stringResource(id = R.string.search_using_camera),
            elevation = 0.dp,
            backgroundColor = White10,
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_camera), contentDescription = "")
        }
    }
}
