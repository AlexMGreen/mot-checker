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
    private val completedDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
    val parsedCompletedDate: LocalDate = LocalDate.parse(completedDate, completedDateFormatter)

    val odometerUnreadable = odometerResultType == "UNREADABLE"

    private val expiryDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val parsedExpiryDate: LocalDate?
        get() = expiryDate?.let { expiryDate ->
            LocalDate.parse(expiryDate, expiryDateFormatter)
        }

    val didPass = testResult == "PASSED"
}

data class ReasonForRejectionAndComment(
    val dangerous: Boolean,
    val text: String,
    val type: String
)
