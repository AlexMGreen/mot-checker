package io.agapps.core.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MotHistoryDto(
    @Json(name = "registration")
    val registration: String,
    @Json(name = "make")
    val make: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "engineSize")
    val engineSize: String?,
    @Json(name = "firstUsedDate")
    val firstUsedDate: String?,
    @Json(name = "fuelType")
    val fuelType: String,
    @Json(name = "manufactureDate")
    val manufactureDate: String,
    @Json(name = "motTests")
    val motTests: List<MotTestDto>?,
    @Json(name = "primaryColour")
    val primaryColour: String,
    @Json(name = "registrationDate")
    val registrationDate: String,
    @Json(name = "vehicleId")
    val vehicleId: String?,
    // Present if vehicle is new and has not yet had an MOT test
    @Json(name = "motTestDueDate")
    val motTestDueDate: String?
)

@JsonClass(generateAdapter = true)
data class MotTestDto(
    @Json(name = "completedDate")
    val completedDate: String,
    @Json(name = "expiryDate")
    val expiryDate: String?,
    @Json(name = "motTestNumber")
    val motTestNumber: String,
    @Json(name = "odometerResultType")
    val odometerResultType: String,
    @Json(name = "odometerUnit")
    val odometerUnit: String?,
    @Json(name = "odometerValue")
    val odometerValue: String,
    @Json(name = "rfrAndComments")
    val rfrAndComments: List<RfrAndCommentDto>,
    @Json(name = "testResult")
    val testResult: String
)

@JsonClass(generateAdapter = true)
data class RfrAndCommentDto(
    @Json(name = "dangerous")
    val dangerous: Boolean,
    @Json(name = "text")
    val text: String,
    @Json(name = "type")
    val type: String
)
