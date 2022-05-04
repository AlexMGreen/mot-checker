package io.agapps.data.vehicledetails

import io.agapps.data.mothistory.MotHistoryDto
import io.agapps.data.mothistory.MotHistoryService
import io.agapps.domain.Failure
import io.agapps.domain.Result
import io.agapps.domain.Success
import io.agapps.domain.vehicledetails.VehicleDetails
import io.agapps.domain.vehicledetails.VehicleDetailsRepository
import java.io.IOException
import javax.inject.Inject

class VehicleDetailsRepositoryImpl @Inject constructor(private val motHistoryService: MotHistoryService) : VehicleDetailsRepository {

    override suspend fun getVehicleDetails(registrationNumber: String): Result<VehicleDetails> {
        val response = motHistoryService.getMotHistory(registrationNumber)
        return if (response.isSuccessful) {
            Success(response.body()!!.first().toDomain())
        } else {
            Failure(IOException(response.errorBody().toString()))
        }
    }

    // TODO: Combine with DVLA Vehicle Enquiry Service API info?
    private fun MotHistoryDto.toDomain() = VehicleDetails(
        registration,
        make,
        model,
        primaryColour,
        fuelType,
        engineSize,
        manufactureDate
    )
}