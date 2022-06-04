package io.agapps.feature.recentvehicles.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.core.model.Vehicle
import io.agapps.core.ui.theme.White50

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecentVehicleCard(
    modifier: Modifier = Modifier,
    vehicle: Vehicle,
    onClick: (Vehicle) -> Unit,
) {
    Card(
        elevation = 1.dp,
        modifier = modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clickable { onClick(vehicle) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = vehicle.registrationNumber,
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
                Text(
                    text = vehicle.capitalisedMakeAndModel,
                    color = White50,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
            Icon(
                Icons.Filled.ArrowForward,
                "",
                tint = White50,
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 4.dp, horizontal = 0.dp)
            )
        }
    }
}

@Preview
@Composable
fun RecentVehicleCardPreview() = RecentVehicleCard(vehicle = Vehicle.vehiclePreview()) {}
