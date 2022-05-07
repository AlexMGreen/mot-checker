package io.agapps.domain.vehicledetails

data class MotTest(
    val completedDate: String,
    val expiryDate: String?,
    val motTestNumber: String,
    val odometerUnit: String,
    val odometerValue: String,
    val reasonForRejectionAndComment: List<ReasonForRejectionAndComment>,
    val testResult: String
)

data class ReasonForRejectionAndComment(
    val dangerous: Boolean,
    val text: String,
    val type: String
)
