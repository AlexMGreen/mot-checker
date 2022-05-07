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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import io.agapps.domain.vehicledetails.MotTest
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.components.IconLabel
import io.agapps.motchecker.ui.theme.Shapes
import io.agapps.motchecker.ui.theme.SurfaceGrey
import io.agapps.motchecker.ui.theme.Typography
import io.agapps.motchecker.ui.theme.White50
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun VehicleMileage(motTests: List<MotTest>, parsedManufactureDate: LocalDate, maxMileage: String, modifier: Modifier = Modifier) {
    val entries = mutableListOf(Entry(parsedManufactureDate.toEpochDay().toFloat(), 0f)) + motTests
        .filter { !it.odometerUnreadable }
        .distinctBy { it.parsedCompletedDate }
        .sortedBy { it.parsedCompletedDate }
        .map { Entry(it.parsedCompletedDate.toEpochDay().toFloat(), it.odometerValue.toFloat()) }

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

            // TODO: Check odometerUnit for mi/km
            IconLabel(
                label = stringResource(R.string.total_miles, maxMileage),
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
                },
                update = { lineChart ->
                    lineChart.data = LineData(getDataSet(entries))
                    lineChart.notifyDataSetChanged()
                    lineChart.invalidate()
                }
            )
        }
    }
}

private fun getDataSet(entries: List<Entry>) = LineDataSet(entries, "").apply {
    mode = LineDataSet.Mode.CUBIC_BEZIER
    cubicIntensity = 0.1f
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
    xAxis.textColor = ContextCompat.getColor(context, R.color.white)
    xAxis.yOffset = 8f
    xAxis.valueFormatter = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return LocalDate.ofEpochDay(value.toLong()).formatMonthYear()
        }
    }
    xAxis.setLabelCount(6, true)
    xAxis.setAvoidFirstLastClipping(true)
    axisRight.xOffset = 8f
    axisRight.enableGridDashedLine(20f, 10f, 10f)
    axisRight.textColor = ContextCompat.getColor(context, R.color.white)
    axisRight.axisMinimum = 0f
    axisLeft.axisMinimum = 0f
    axisLeft.isEnabled = false
    extraRightOffset = 8f
    extraBottomOffset = 12f
    description.isEnabled = false
    legend.isEnabled = false
    data = LineData(dataset)
    setScaleEnabled(false)
    invalidate()
}


fun LocalDate.formatMonthYear(): String = monthYearFormatter.format(this)

private val monthYearFormatter = DateTimeFormatter.ofPattern("MM-yyyy")
