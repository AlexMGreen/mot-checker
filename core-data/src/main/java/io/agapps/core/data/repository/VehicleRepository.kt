package io.agapps.core.data.repository

import io.agapps.core.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    fun getVehicle(registrationNumber: String): Flow<Vehicle?>

    suspend fun updateVehicle(registrationNumber: String)
}
