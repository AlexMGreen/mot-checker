package io.agapps.motchecker.search.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.components.IconLabel
import io.agapps.motchecker.ui.theme.MOTCheckerTheme
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.SurfaceGrey
import io.agapps.motchecker.ui.theme.Typography
import io.agapps.motchecker.ui.theme.White50

@Composable
fun VehicleMileage(vehicleDetails: VehicleDetails, modifier: Modifier = Modifier) {
    val entries = listOf(
        Entry(0f, 0f),
        Entry(1f, 7430f),
        Entry(2f, 18000f),
        Entry(3f, 35430f),
    )

    Column {
        Spacer(modifier = Modifier.size(16.dp))

        Row {
            Text(
                text = stringResource(R.string.mileage),
                style = Typography.subtitle1,
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            IconLabel(
                label = "35430 miles",
                icon = {
                    Icon(Icons.Outlined.Info, "")
                }
            )
        }

        Surface(color = SurfaceGrey, modifier = modifier.padding(8.dp), shape = Shapes.medium) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(196.dp),
                factory = { context ->
                    getLineChart(context, getDataSet(entries))
                }
            )
        }
    }
}

private fun getDataSet(entries: List<Entry>) = LineDataSet(entries, "").apply {
    mode = LineDataSet.Mode.CUBIC_BEZIER
    circleColors = listOf(Color.WHITE)
    color = Color.WHITE
    fillDrawable = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(White50.toArgb(), Color.TRANSPARENT)
    )
    isHighlightEnabled = false
    setDrawValues(false)
    setDrawFilled(true)
}

private fun getLineChart(context: Context, dataset: LineDataSet) = LineChart(context).apply {
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.granularity = 1f
    xAxis.enableGridDashedLine(20f, 10f, 10f)
    axisRight.enableGridDashedLine(20f, 10f, 10f)
    xAxis.textColor = ContextCompat.getColor(context, R.color.white)
    xAxis.yOffset = 8f
    axisRight.xOffset = 8f
    extraBottomOffset = 12f
    axisRight.textColor = ContextCompat.getColor(context, R.color.white)
    axisRight.axisMinimum = 0f
    axisLeft.axisMinimum = 0f
    axisLeft.isEnabled = false
    description.isEnabled = false
    legend.isEnabled = false
    extraRightOffset = 8f
    data = LineData(dataset)
    setScaleEnabled(false)
    invalidate()
}

@Preview
@Composable
fun PreviewVehicleMileage() {
    MOTCheckerTheme {
        VehicleMileage(VehicleDetails.vehiclePreview())
    }
}
