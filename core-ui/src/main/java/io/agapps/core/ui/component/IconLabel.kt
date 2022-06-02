package io.agapps.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.motchecker.ui.theme.Shapes

@Composable
fun IconLabel(
    modifier: Modifier = Modifier,
    label: String,
    elevation: Dp = 0.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    icon: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = Shapes.medium,
        backgroundColor = backgroundColor,
        elevation = elevation,
    ) {
        Row(modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(16.dp)) {
                icon()
            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun IconLabelPreview() {
    IconLabel(label = "A label") {
        Icon(Icons.Default.Search, "")
    }
}
