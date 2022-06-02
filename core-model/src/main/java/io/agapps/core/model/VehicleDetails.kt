package io.agapps.core.model

import java.time.Clock
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

    private val clock = Clock.systemDefaultZone()
    // TODO: Check for motTestDueDate if new vehicle without MOT history
    val hasValidMot = motTests
        ?.mapNotNull { it.parsedExpiryDate }
        ?.any { parsedExpiryDate ->
            parsedExpiryDate.isEqual(LocalDate.now(clock)) || parsedExpiryDate.isAfter(LocalDate.now(clock))
        }

    val latestExpiryDate = motTests?.sortedByDescending { it.parsedExpiryDate }?.first()?.parsedExpiryDate

    companion object {
        fun vehiclePreview() = VehicleDetails(
            "AB66XYZ",
            "Ford",
            "Focus",
            "Blue",
            "Petrol",
            "1600",
            "12.06.1990",
            listOf(MotTest.motPreview())
        )
    }
}

private fun String.capitalizeWords(): String = lowercase().split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }
