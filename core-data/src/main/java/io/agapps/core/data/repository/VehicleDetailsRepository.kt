package io.agapps.core.data.repository

import io.agapps.common.result.Result
import io.agapps.core.model.VehicleDetails

interface VehicleDetailsRepository {
    suspend fun getVehicleDetails(registrationNumber: String): Result<VehicleDetails>
}
