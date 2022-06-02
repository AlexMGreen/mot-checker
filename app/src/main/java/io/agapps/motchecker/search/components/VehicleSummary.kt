package io.agapps.motchecker.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import io.agapps.core.model.VehicleDetails
import io.agapps.core.ui.component.IconLabel
import io.agapps.motchecker.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VehicleSummary(vehicleDetails: VehicleDetails, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = vehicleDetails.capitalisedMakeAndModel,
            color = Color.White,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )

        FlowRow {
            IconLabel(modifier, vehicleDetails.primaryColour) { Icon(painterResource(id = R.drawable.ic_color), "") }
            IconLabel(modifier, vehicleDetails.parsedManufactureDate.formatDayMonthYear()) { Icon(painterResource(id = R.drawable.ic_calendar), "") }
        }
        FlowRow {
            IconLabel(modifier, vehicleDetails.fuelType) { Icon(painterResource(id = R.drawable.ic_fuel), "") }
            vehicleDetails.engineSizeCc?.let { engineSizeCc ->
                IconLabel(modifier, "${engineSizeCc}cc") { Icon(Icons.Outlined.Info, "") }
            }
        }
    }
}

fun LocalDate.formatDayMonthYear(): String = dateFormatter.format(this)

private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
