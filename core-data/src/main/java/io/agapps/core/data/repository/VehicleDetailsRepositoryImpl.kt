package io.agapps.core.data.repository

import io.agapps.common.result.Failure
import io.agapps.common.result.Result
import io.agapps.common.result.Success
import io.agapps.core.database.dao.VehicleDetailsDao
import io.agapps.core.database.model.VehicleDetailsEntity
import io.agapps.core.model.MotTest
import io.agapps.core.model.ReasonForRejectionAndComment
import io.agapps.core.model.VehicleDetails
import io.agapps.core.network.model.MotHistoryDto
import io.agapps.core.network.model.MotTestDto
import io.agapps.core.network.model.RfrAndCommentDto
import io.agapps.core.network.service.MotHistoryService
import java.io.IOException
import javax.inject.Inject

class VehicleDetailsRepositoryImpl @Inject constructor(
    private val motHistoryService: MotHistoryService,
    private val vehicleDetailsDao: VehicleDetailsDao,
) : VehicleDetailsRepository {

    override suspend fun getVehicleDetails(registrationNumber: String): Result<VehicleDetails> {
        val response = motHistoryService.getMotHistory(registrationNumber)
        return if (response.isSuccessful) {
            val vehicleDetails = response.body()!!.first().toDomain()
            vehicleDetailsDao.insertVehicle(vehicleDetails.toEntity())
            Success(vehicleDetails)
        } else {
            Failure(IOException(response.errorBody().toString()))
        }
    }

    private fun VehicleDetails.toEntity() = VehicleDetailsEntity(
        registrationNumber, make, model, primaryColour, fuelType, engineSizeCc, manufactureDate
    )

    // TODO: Combine with DVLA Vehicle Enquiry Service API info?
    private fun MotHistoryDto.toDomain() = VehicleDetails(
        registration,
        make,
        model,
        primaryColour,
        fuelType,
        engineSize,
        manufactureDate,
        motTests = this.motTests?.map { it.toDomain() }
    )

    private fun MotTestDto.toDomain() = MotTest(
        completedDate,
        expiryDate,
        motTestNumber,
        odometerUnit,
        odometerValue,
        odometerResultType,
        reasonForRejectionAndComment = rfrAndComments.map { it.toDomain() },
        testResult
    )

    private fun RfrAndCommentDto.toDomain() = ReasonForRejectionAndComment(
        dangerous,
        text,
        type
    )
}
