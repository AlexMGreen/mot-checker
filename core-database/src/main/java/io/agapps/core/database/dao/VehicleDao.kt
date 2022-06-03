package io.agapps.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.agapps.core.database.model.VehicleEntity

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    suspend fun getAllVehicles(): List<VehicleEntity>

    @Query("SELECT * FROM vehicle WHERE registration_number LIKE :registrationNumber LIMIT 1")
    suspend fun getVehicleByRegistrationNumber(registrationNumber: String): VehicleEntity

    @Query("SELECT * FROM vehicle WHERE registration_number IN (:registrationNumbers)")
    suspend fun getAllVehiclesByRegistrationNumbers(registrationNumbers: List<String>): List<VehicleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)
}
