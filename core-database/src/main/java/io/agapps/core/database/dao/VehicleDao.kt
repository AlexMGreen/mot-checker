package io.agapps.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.agapps.core.database.model.VehicleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicle WHERE registration_number = :registrationNumber")
    fun getVehicleByRegistrationNumber(registrationNumber: String): Flow<VehicleEntity?>

    @Query("SELECT * FROM vehicle WHERE registration_number IN (:registrationNumbers)")
    fun getAllVehiclesByRegistrationNumbers(registrationNumbers: List<String>): Flow<List<VehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)
}
