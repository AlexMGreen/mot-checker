package io.agapps.feature.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.core.model.VehicleDetails
import io.agapps.core.ui.theme.Typography
import io.agapps.feature.search.R
import io.agapps.motchecker.ui.theme.Green50
import io.agapps.motchecker.ui.theme.Red50

@Composable
fun MotStatus(
    vehicleDetails: VehicleDetails,
    modifier: Modifier = Modifier
) {
    val validMot = vehicleDetails.hasValidMot ?: return
    if (validMot) {
        MotStatusCard(
            text = stringResource(R.string.mot_valid_until, vehicleDetails.latestExpiryDate?.formatDayMonthYear().toString()),
            icon = { Icon(Icons.Outlined.CheckCircle, "", modifier = Modifier.size(48.dp)) },
            backgroundColor = Green50,
            modifier = modifier
        )
    } else {
        MotStatusCard(
            text = stringResource(R.string.mot_expired_on, vehicleDetails.latestExpiryDate?.formatDayMonthYear().toString()),
            icon = {
                Icon(
                    painter = painterResource(id = io.agapps.core.ui.R.drawable.ic_cross_filled),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            },
            backgroundColor = Red50,
            modifier = modifier
        )
    }
}

@Composable
private fun MotStatusCard(
    text: String,
    icon: @Composable () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = backgroundColor
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            icon()

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = text,
                style = Typography.subtitle1,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
