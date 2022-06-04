package io.agapps.feature.recentvehicles.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.component.LabelButton
import io.agapps.core.ui.component.SectionTitle
import io.agapps.feature.recentvehicles.R

@Composable
fun RecentVehicleList(
    modifier: Modifier = Modifier,
    vehicles: List<Vehicle>,
    onVehicleClicked: (Vehicle) -> Unit,
    onViewAllClicked: () -> Unit,
) {
    if (vehicles.isEmpty()) return
    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
        SectionTitle(title = stringResource(id = R.string.recent_vehicles), modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.weight(1f))
        LabelButton(text = stringResource(id = R.string.view_all)) {
            onViewAllClicked()
        }
    }
    vehicles.forEach {
        RecentVehicleCard(vehicle = it, modifier = modifier.padding(horizontal = 16.dp)) { clickedVehicle ->
            onVehicleClicked(clickedVehicle)
        }
    }
}