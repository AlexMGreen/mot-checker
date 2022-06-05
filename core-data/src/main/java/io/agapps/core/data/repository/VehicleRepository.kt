package io.agapps.core.data.repository

import io.agapps.core.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun getVehicle(registrationNumber: String, shouldUpdate: Boolean = true): Flow<Vehicle>
}
