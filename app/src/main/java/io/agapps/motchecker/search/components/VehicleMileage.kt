package io.agapps.motchecker.search.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
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
import io.agapps.motchecker.ui.components.SectionTitle
import io.agapps.motchecker.ui.theme.Shapes
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

        Row {
            SectionTitle(
                title = stringResource(R.string.mileage),
                modifier = Modifier
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

        Card(elevation = 0.dp, modifier = modifier.padding(8.dp), shape = Shapes.medium) {
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

private const val LineCubitIntensity = 0.1f
private fun getDataSet(entries: List<Entry>) = LineDataSet(entries, "").apply {
    mode = LineDataSet.Mode.CUBIC_BEZIER
    cubicIntensity = LineCubitIntensity
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

private const val XAxisGranularity = 1f
private const val DashedLineLength = 20f
private const val DashedLineSpaceLength = 10f
private const val DashedLinePhase = 10f
private const val XAxisOffset = 8f
private const val XAxisLabelCount = 6
private const val RightAxisOffset = 8f
private const val RightAxisMinimum = 0f
private const val LeftAxisMinimum = 0f
private const val ExtraRightOffset = 8f
private const val ExtraBottomOffset = 12f

private fun getLineChart(context: Context, dataset: LineDataSet) = LineChart(context).apply {
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.granularity = XAxisGranularity
    xAxis.enableGridDashedLine(DashedLineLength, DashedLineSpaceLength, DashedLinePhase)
    xAxis.textColor = ContextCompat.getColor(context, R.color.white)
    xAxis.yOffset = XAxisOffset
    xAxis.valueFormatter = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return LocalDate.ofEpochDay(value.toLong()).formatMonthYear()
        }
    }
    xAxis.setLabelCount(XAxisLabelCount, true)
    xAxis.setAvoidFirstLastClipping(true)
    axisRight.xOffset = RightAxisOffset
    axisRight.enableGridDashedLine(DashedLineSpaceLength, DashedLineSpaceLength, DashedLinePhase)
    axisRight.textColor = ContextCompat.getColor(context, R.color.white)
    axisRight.axisMinimum = RightAxisMinimum
    axisLeft.axisMinimum = LeftAxisMinimum
    axisLeft.isEnabled = false
    extraRightOffset = ExtraRightOffset
    extraBottomOffset = ExtraBottomOffset
    description.isEnabled = false
    legend.isEnabled = false
    data = LineData(dataset)
    setScaleEnabled(false)
    invalidate()
}

fun LocalDate.formatMonthYear(): String = monthYearFormatter.format(this)

private val monthYearFormatter = DateTimeFormatter.ofPattern("MM-yyyy")
