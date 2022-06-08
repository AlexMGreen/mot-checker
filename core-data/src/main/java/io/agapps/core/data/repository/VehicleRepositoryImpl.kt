package io.agapps.core.data.repository

import com.github.ajalt.timberkt.Timber
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.MotTestEntity
import io.agapps.core.database.model.RfrAndCommentEntity
import io.agapps.core.database.model.VehicleEntity
import io.agapps.core.database.model.toDomain
import io.agapps.core.model.MotTest
import io.agapps.core.model.ReasonForRejectionAndComment
import io.agapps.core.model.Vehicle
import io.agapps.core.network.model.MotHistoryDto
import io.agapps.core.network.model.MotTestDto
import io.agapps.core.network.model.RfrAndCommentDto
import io.agapps.core.network.service.MotHistoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val motHistoryService: MotHistoryService,
    private val vehicleDao: VehicleDao,
) : VehicleRepository {

    override fun getVehicle(registrationNumber: String): Flow<Vehicle?> {
        return vehicleDao.getVehicleByRegistrationNumber(registrationNumber.uppercase()).distinctUntilChanged().map {
            Timber.d { "Retrieved vehicle from db: $it" }
            it?.toDomain()
        }
    }

    override suspend fun updateVehicle(registrationNumber: String) {
        Timber.d { "Updating vehicle from API: $registrationNumber" }
        val response = motHistoryService.getMotHistory(registrationNumber)
        if (response.isSuccessful) {
            val vehicle = response.body()!!.first().toDomain()
            Timber.d { "API response successful, saving vehicle in DB: $registrationNumber" }
            vehicleDao.insertVehicle(vehicle.toEntity())
        }
    }

    private fun Vehicle.toEntity() = VehicleEntity(
        registrationNumber = registrationNumber.uppercase(),
        make = make,
        model = model,
        primaryColour = primaryColour,
        fuelType = fuelType,
        engineSizeCc = engineSizeCc,
        manufactureDate = manufactureDate,
        motTests = motTests?.map { it.toEntity() }
    )

    private fun MotTest.toEntity() = MotTestEntity(
        completedDate = completedDate,
        expiryDate = expiryDate,
        motTestNumber = motTestNumber,
        odometerUnit = odometerUnit,
        odometerResultType = odometerResultType,
        odometerValue = odometerValue,
        rfrAndComments = reasonForRejectionAndComment.map { it.toEntity() },
        testResult = testResult,
    )

    private fun ReasonForRejectionAndComment.toEntity() = RfrAndCommentEntity(
        dangerous = dangerous,
        text = text,
        type = type,
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
