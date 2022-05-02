package io.agapps.domain.vehicledetails

data class VehicleDetails(
    val registrationNumber: String,
    val make: String,
    val model: String,
    val primaryColour: String,
    val fuelType: String,
    val engineSizeCc: String,
    val manufactureDate: String,
)
