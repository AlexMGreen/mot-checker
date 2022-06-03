package io.agapps.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.agapps.core.database.model.VehicleDetailsEntity

@Dao
interface VehicleDetailsDao {
    @Query("SELECT * FROM vehicle_details")
    suspend fun getAllVehicles(): List<VehicleDetailsEntity>

    @Query("SELECT * FROM vehicle_details WHERE registration_number LIKE :registrationNumber LIMIT 1")
    suspend fun getVehicleByRegistrationNumber(registrationNumber: String): VehicleDetailsEntity

    @Query("SELECT * FROM vehicle_details WHERE registration_number IN (:registrationNumbers)")
    suspend fun getAllVehiclesByRegistrationNumbers(registrationNumbers: List<String>): List<VehicleDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicleDetails: VehicleDetailsEntity)

    @Delete
    suspend fun delete(vehicleDetails: VehicleDetailsEntity)
}
