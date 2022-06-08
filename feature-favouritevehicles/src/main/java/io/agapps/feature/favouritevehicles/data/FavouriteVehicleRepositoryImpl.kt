package io.agapps.feature.favouritevehicles.data

import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.toDomain
import io.agapps.core.model.Vehicle
import io.agapps.feature.favouritevehicles.database.FavouriteVehicleDao
import io.agapps.feature.favouritevehicles.database.FavouriteVehicleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteVehicleRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao,
    private val favouriteVehicleDao: FavouriteVehicleDao,
) : FavouriteVehicleRepository {

    override fun getFavouriteVehicle(registration: String) = favouriteVehicleDao.getFavouriteVehicle(registration.uppercase())
        .distinctUntilChanged()
        .flatMapMerge { favouriteVehicleEntity ->
            if (favouriteVehicleEntity == null) {
                flowOf(null)
            } else {
                vehicleDao.getVehicleByRegistrationNumber(favouriteVehicleEntity.vehicleRegistrationNumber).map { it?.toDomain() }
            }
        }

    override fun getFavouriteVehicles(limit: Int?): Flow<List<Vehicle>> {
        val favouriteVehicles = if (limit != null) favouriteVehicleDao.getFavouriteVehicles(limit) else favouriteVehicleDao.getAllFavouriteVehicles()
        return favouriteVehicles
            .distinctUntilChanged()
            .flatMapMerge { favouriteVehicleEntities ->
                val favouriteRegistrations = favouriteVehicleEntities.map { it.vehicleRegistrationNumber }
                vehicleDao.getAllVehiclesByRegistrationNumbers(favouriteRegistrations).map { vehicleEntities ->
                    vehicleEntities.map { it.toDomain() }
                }
            }
    }

    override suspend fun addFavouriteVehicle(vehicle: Vehicle) {
        favouriteVehicleDao.insertFavouriteVehicle(FavouriteVehicleEntity(vehicleRegistrationNumber = vehicle.registrationNumber.uppercase()))
    }

    override suspend fun removeFavouriteVehicle(vehicle: Vehicle) {
        favouriteVehicleDao.deleteFavouriteVehicle(FavouriteVehicleEntity(vehicleRegistrationNumber = vehicle.registrationNumber.uppercase()))
    }
}

