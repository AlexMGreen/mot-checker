package io.agapps.feature.favouritevehicles.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.agapps.core.ui.component.SectionTitle
import io.agapps.core.ui.component.buttons.LabelButton
import io.agapps.feature.favouritevehicles.R

@Composable
fun FavouriteVehicleSectionHeader(
    modifier: Modifier = Modifier,
    onViewAllClicked: () -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 12.dp)) {
        SectionTitle(title = stringResource(id = R.string.favourite_vehicles), modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.weight(1f))
        LabelButton(text = stringResource(id = io.agapps.core.ui.R.string.view_all)) {
            onViewAllClicked()
        }
    }
}
