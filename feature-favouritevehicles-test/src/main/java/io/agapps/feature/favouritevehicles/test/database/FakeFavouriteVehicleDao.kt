package io.agapps.feature.favouritevehicles.test.database

import io.agapps.feature.favouritevehicles.database.FavouriteVehicleDao
import io.agapps.feature.favouritevehicles.database.FavouriteVehicleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeFavouriteVehicleDao : FavouriteVehicleDao {

    private val fakeFavouriteEntities = MutableStateFlow(emptySet<FavouriteVehicleEntity>())

    override fun getFavouriteVehicle(registrationNumber: String) = fakeFavouriteEntities.map { entities ->
        entities.firstOrNull { entity ->
            entity.vehicleRegistrationNumber == registrationNumber
        }
    }

    override fun getFavouriteVehicles(limit: Int) = fakeFavouriteEntities.map { entities ->
        entities.toList().take(limit)
    }

    override fun getAllFavouriteVehicles() = fakeFavouriteEntities.map { entities ->
        entities.toList()
    }

    override suspend fun insertFavouriteVehicle(favouriteVehicle: FavouriteVehicleEntity) {
        fakeFavouriteEntities.value = fakeFavouriteEntities.value.plus(favouriteVehicle)
    }

    override suspend fun deleteFavouriteVehicle(favouriteVehicle: FavouriteVehicleEntity) {
        fakeFavouriteEntities.value = fakeFavouriteEntities.value.minus(favouriteVehicle)
    }
}
