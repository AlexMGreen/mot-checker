package io.agapps.core.ui.extensions

import androidx.compose.foundation.lazy.LazyListState
import io.agapps.common.mapRange

private const val DefaultScrollLimit = 300f

fun LazyListState.toFadeInOnScrollAlpha(maxScrollLimit: Float = DefaultScrollLimit): Float {
    return if (firstVisibleItemIndex > 0) {
        1f
    } else {
        firstVisibleItemScrollOffset.toFloat().mapRange(0f, maxScrollLimit, 0f, 1f).coerceIn(0f, 1f)
    }
}

fun LazyListState.toFadeOutOnScrollAlpha(maxScrollLimit: Float = DefaultScrollLimit): Float {
    return if (firstVisibleItemIndex > 0) {
        0f
    } else {
        1f - firstVisibleItemScrollOffset.toFloat().mapRange(0f, maxScrollLimit, 0f, 1f).coerceIn(0f, 1f)
    }
}
