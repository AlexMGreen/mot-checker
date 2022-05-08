package io.agapps.motchecker.search.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.theme.Green50
import io.agapps.motchecker.ui.theme.Typography

@Composable
fun MotStatus(
    vehicleDetails: VehicleDetails,
    modifier: Modifier = Modifier
) {
    val validMot = vehicleDetails.hasValidMot ?: return
    if (validMot) {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = Green50
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    "",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = stringResource(R.string.mot_valid_until, vehicleDetails.latestExpiryDate?.formatDayMonthYear().toString()),
                    style = Typography.subtitle1,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}
