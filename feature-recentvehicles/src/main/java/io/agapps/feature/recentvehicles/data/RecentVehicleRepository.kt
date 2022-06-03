package io.agapps.feature.recentvehicles.data

import io.agapps.core.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface RecentVehicleRepository {
    fun getRecentVehicles(limit: Int? = null): Flow<List<Vehicle>>

    suspend fun addRecentVehicle(vehicle: Vehicle)
}

