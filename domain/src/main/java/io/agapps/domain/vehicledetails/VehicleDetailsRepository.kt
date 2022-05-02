package io.agapps.domain.vehicledetails

import io.agapps.domain.Result

interface VehicleDetailsRepository {
    suspend fun getVehicleDetails(registrationNumber: String): Result<VehicleDetails>
}
