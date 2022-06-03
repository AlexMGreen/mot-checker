package io.agapps.core.data.repository

import io.agapps.common.result.Result
import io.agapps.core.model.Vehicle

interface VehicleRepository {
    suspend fun getVehicle(registrationNumber: String): Result<Vehicle>
}
