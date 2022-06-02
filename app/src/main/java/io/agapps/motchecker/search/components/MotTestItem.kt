package io.agapps.motchecker.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.agapps.core.model.MotTest
import io.agapps.core.model.ReasonForRejectionAndComment
import io.agapps.core.ui.component.IconLabel
import io.agapps.core.ui.theme.Typography
import io.agapps.motchecker.R
import io.agapps.motchecker.ui.theme.Green50
import io.agapps.motchecker.ui.theme.MOTCheckerTheme
import io.agapps.motchecker.ui.theme.Red50

@Composable
fun MotTestItem(
    motTest: MotTest,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        IconLabel(label = motTest.parsedCompletedDate.formatDayMonthYear()) {
            Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "")
        }

        Card(
            elevation = 0.dp,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Row {
                Surface(
                    color = if (motTest.didPass) Green50 else Red50,
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                ) {}

                Column {
                    MotTestItemHeadline(motTest = motTest, modifier = modifier)
                    MotTestItemBody(motTest = motTest, modifier = modifier)
                    MotTestItemComments(motTest, modifier)
                    Spacer(modifier = Modifier.size(8.dp))
                    MotTestItemFooter(motTest = motTest, modifier = modifier)
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }
    }
}

@Composable
fun MotTestItemHeadline(
    motTest: MotTest,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        if (motTest.didPass) {
            IconLabel(label = stringResource(R.string.passed), backgroundColor = Green50) {
                Icon(Icons.Outlined.CheckCircle, "")
            }
        } else {
            IconLabel(label = stringResource(R.string.failed), backgroundColor = Red50) {
                Icon(painter = painterResource(id = R.drawable.ic_cross_filled), contentDescription = "")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconLabel(
            label = stringResource(R.string.test_miles, motTest.odometerValue),
            elevation = 1.dp,
            icon = { Icon(Icons.Outlined.Info, "") }
        )
    }
}

@Composable
fun MotTestItemBody(
    motTest: MotTest,
    modifier: Modifier = Modifier
) {
    // TODO: Display mileage diff from last test

    if (!motTest.didPass) return
    Row(modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
        Column {
            Text(text = stringResource(R.string.expiry_date), style = Typography.overline)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = motTest.parsedExpiryDate?.formatDayMonthYear().orEmpty(), style = Typography.body2)
        }
    }
}

@Composable
fun MotTestItemComments(
    motTest: MotTest,
    modifier: Modifier = Modifier
) {
    Column {
        MotTestItemCommentsItem( stringResource(R.string.dangerous_defects), motTest.dangerousDefects, modifier)
        MotTestItemCommentsItem( stringResource(R.string.major_defects), motTest.majorDefects, modifier)
        MotTestItemCommentsItem( stringResource(R.string.minor_defects), motTest.minorDefects, modifier)
        MotTestItemCommentsItem( stringResource(R.string.advisories), motTest.advisories, modifier)
    }
}

@Composable
fun MotTestItemCommentsItem(
    label: String,
    rfrAndComments: List<ReasonForRejectionAndComment>,
    modifier: Modifier = Modifier
) {
    if (rfrAndComments.isEmpty()) return
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size(12.dp))
        IconLabel(label = label, elevation = 1.dp) {
            Icon(Icons.Outlined.Info, "")
        }
        Card(
            elevation = 1.dp,
            modifier = modifier.padding(horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                rfrAndComments.forEach { major ->
                    Text(text = major.text, style = Typography.body2, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun MotTestItemFooter(
    motTest: MotTest,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
        Text(text = stringResource(R.string.mot_test_number), style = Typography.overline)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = motTest.motTestNumber, style = Typography.body2)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMotTestItem() {
    MOTCheckerTheme {
        MotTestItem(motTest = MotTest.motPreview())
    }
}
