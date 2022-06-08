package io.agapps.feature.favouritevehicles.data

import io.agapps.core.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface FavouriteVehicleRepository {
    fun getFavouriteVehicles(limit: Int? = null): Flow<List<Vehicle>>

    fun getFavouriteVehicle(registration: String): Flow<Vehicle?>

    suspend fun addFavouriteVehicle(vehicle: Vehicle)

    suspend fun removeFavouriteVehicle(vehicle: Vehicle)
}

