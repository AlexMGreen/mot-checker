package io.agapps.feature.favouritevehicles.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteVehicleDao {
    @Query("SELECT * FROM favourite_vehicle WHERE vehicle_registration_number = :registrationNumber")
    fun getFavouriteVehicle(registrationNumber: String): Flow<FavouriteVehicleEntity?>

    @Query("SELECT * FROM favourite_vehicle LIMIT :limit")
    fun getFavouriteVehicles(limit: Int): Flow<List<FavouriteVehicleEntity>>

    @Query("SELECT * FROM favourite_vehicle")
    fun getAllFavouriteVehicles(): Flow<List<FavouriteVehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteVehicle(favouriteVehicle: FavouriteVehicleEntity)

    @Delete
    suspend fun deleteFavouriteVehicle(favouriteVehicle: FavouriteVehicleEntity)
}
