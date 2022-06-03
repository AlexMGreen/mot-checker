package io.agapps.feature.recentvehicles.data

import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.model.Vehicle
import io.agapps.feature.recentvehicles.database.RecentVehicleDao
import io.agapps.feature.recentvehicles.database.RecentVehicleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecentVehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao,
    private val recentVehicleDao: RecentVehicleDao,
) : RecentVehicleRepository {

    override fun getRecentVehicles(limit: Int?): Flow<List<Vehicle>> {
        val recentVehicles = if (limit != null) recentVehicleDao.getRecentVehicles(limit) else recentVehicleDao.getAllRecentVehicles()

        return recentVehicles
            .distinctUntilChanged()
            .flatMapMerge { recentVehicleEntities ->
                val entities = recentVehicleEntities.map { recentEntity ->
                    vehicleDao.getVehicleByRegistrationNumber(recentEntity.vehicleRegistrationNumber).map { vehicleEntity ->
                        recentEntity to vehicleEntity
                    }
                }

                combine(entities) { combined ->
                    combined.sortedByDescending { recentToVehiclePair ->
                        recentToVehiclePair.first.lastSearchedDate
                    }.map {
                        it.second.toDomain()
                    }
                }
            }
    }

    override suspend fun addRecentVehicle(vehicle: Vehicle) {
        recentVehicleDao.insertRecentVehicle(RecentVehicleEntity(vehicleRegistrationNumber = vehicle.registrationNumber))
    }
}
