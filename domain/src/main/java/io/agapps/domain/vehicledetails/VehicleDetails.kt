package io.agapps.domain.vehicledetails

import java.util.*

data class VehicleDetails(
    val registrationNumber: String,
    val make: String,
    val model: String,
    val primaryColour: String,
    val fuelType: String,
    val engineSizeCc: String?,
    val manufactureDate: String,
) {
    val capitalisedMakeAndModel = "$make $model".capitalizeWords()
}

private fun String.capitalizeWords(): String = lowercase().split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }
