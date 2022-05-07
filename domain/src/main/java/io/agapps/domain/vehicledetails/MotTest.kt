package io.agapps.domain.vehicledetails

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MotTest(
    val completedDate: String,
    val expiryDate: String?,
    val motTestNumber: String,
    val odometerUnit: String?,
    val odometerValue: String,
    val odometerResultType: String,
    val reasonForRejectionAndComment: List<ReasonForRejectionAndComment>,
    val testResult: String
) {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
    val parsedCompletedDate: LocalDate = LocalDate.parse(completedDate, dateFormatter)

    val odometerUnreadable = odometerResultType == "UNREADABLE"
}

data class ReasonForRejectionAndComment(
    val dangerous: Boolean,
    val text: String,
    val type: String
)
