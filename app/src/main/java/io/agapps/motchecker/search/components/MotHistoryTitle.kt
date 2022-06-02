package io.agapps.motchecker.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.agapps.core.model.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.components.IconLabel
import io.agapps.motchecker.ui.components.SectionTitle
import io.agapps.motchecker.ui.theme.Green50
import io.agapps.motchecker.ui.theme.Red50

@Composable
fun MotHistoryTitle(
    vehicleDetails: VehicleDetails,
    modifier: Modifier = Modifier
) {
    val tests = vehicleDetails.motTests
    if (tests.isNullOrEmpty()) return

    val totalPassed = tests.count { it.didPass }
    val totalFailed = tests.count { !it.didPass }

    Row(modifier = modifier) {
        SectionTitle(
            title = stringResource(R.string.mot_history),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        IconLabel(label = stringResource(R.string.total_passed, totalPassed), backgroundColor = Green50) {
            Icon(Icons.Outlined.CheckCircle, "")
        }

        IconLabel(label = stringResource(R.string.total_failed, totalFailed), backgroundColor = Red50) {
            Icon(painter = painterResource(id = R.drawable.ic_cross_filled), contentDescription = "")
        }
    }
}

