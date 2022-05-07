package io.agapps.domain.vehicledetails

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class VehicleDetails(
    val registrationNumber: String,
    val make: String,
    val model: String,
    val primaryColour: String,
    val fuelType: String,
    val engineSizeCc: String?,
    val manufactureDate: String,
    val motTests: List<MotTest>?,
) {
    val capitalisedMakeAndModel = "$make $model".capitalizeWords()

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val parsedManufactureDate: LocalDate
        get() = LocalDate.parse(manufactureDate, dateFormatter)

    val maxMileage = motTests?.maxByOrNull { it.odometerValue.toInt() }?.odometerValue


    companion object {
        fun vehiclePreview() = VehicleDetails(
            "AB66XYZ",
            "Ford",
            "Focus",
            "Blue",
            "Petrol",
            "1600",
            "12.06.1990",
            emptyList()
        )
    }
}

private fun String.capitalizeWords(): String = lowercase().split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }
