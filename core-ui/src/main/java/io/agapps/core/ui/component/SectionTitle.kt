package io.agapps.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.core.ui.theme.Typography

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = Typography.overline,
        fontSize = 14.sp,
        modifier = modifier
            .padding(horizontal = 8.dp)
    )
}
