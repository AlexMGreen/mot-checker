package io.agapps.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.agapps.core.ui.R
import io.agapps.motchecker.ui.theme.DarkGrey

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val numberPlateFont = FontFamily(
    Font(R.font.uk_number_plate)
)

val numberPlateTextStyle = TextStyle(
    fontFamily = numberPlateFont,
    fontWeight = FontWeight.Normal,
    textAlign = TextAlign.Center,
    fontSize = 32.sp,
    color = DarkGrey
)
