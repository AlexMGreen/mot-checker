package io.agapps.core.model

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

    // TODO: Check for RFRs with type=="USER ENTERED"

    val dangerousDefects = reasonForRejectionAndComment.filter { it.type == "DANGEROUS" || it.dangerous }

    val majorDefects = reasonForRejectionAndComment.filter { it.type == "MAJOR" || it.type == "PRS" || it.type == "FAIL"}

    val minorDefects = reasonForRejectionAndComment.filter { it.type == "MINOR" }

    val advisories = reasonForRejectionAndComment.filter { it.type == "ADVISORY" }

    companion object {
        fun motPreview() = MotTest(
            "2021.10.05 08:34:38",
            "2022.10.13",
            "224288258889",
            "mi",
            "105263",
            "READ",
            listOf(
                ReasonForRejectionAndComment(
                    dangerous = false,
                    text = "Offside Front Drive shaft joint constant velocity boot severely deteriorated (6.1.7 (g) (i))",
                    type = "MINOR"
                )
            ),
            "PASSED"
        )
    }
}

data class ReasonForRejectionAndComment(
    val dangerous: Boolean,
    val text: String,
    val type: String
)
