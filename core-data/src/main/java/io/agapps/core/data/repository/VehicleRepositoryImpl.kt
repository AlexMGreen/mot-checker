package io.agapps.core.data.repository

import io.agapps.common.result.Failure
import io.agapps.common.result.Result
import io.agapps.common.result.Success
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.VehicleEntity
import io.agapps.core.model.MotTest
import io.agapps.core.model.ReasonForRejectionAndComment
import io.agapps.core.model.Vehicle
import io.agapps.core.network.model.MotHistoryDto
import io.agapps.core.network.model.MotTestDto
import io.agapps.core.network.model.RfrAndCommentDto
import io.agapps.core.network.service.MotHistoryService
import java.io.IOException
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val motHistoryService: MotHistoryService,
    private val vehicleDao: VehicleDao,
) : VehicleRepository {

    override suspend fun getVehicle(registrationNumber: String): Result<Vehicle> {
        val response = motHistoryService.getMotHistory(registrationNumber)
        return if (response.isSuccessful) {
            val vehicle = response.body()!!.first().toDomain()
            vehicleDao.insertVehicle(vehicle.toEntity())
            Success(vehicle)
        } else {
            Failure(IOException(response.errorBody().toString()))
        }
    }

    private fun Vehicle.toEntity() = VehicleEntity(
        registrationNumber, make, model, primaryColour, fuelType, engineSizeCc, manufactureDate
    )

    // TODO: Combine with DVLA Vehicle Enquiry Service API info?
    private fun MotHistoryDto.toDomain() = Vehicle(
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
