package io.agapps.feature.recentvehicles.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentVehicleDao {
    @Query("SELECT * FROM recent_vehicle ORDER BY last_searched_date DESC LIMIT :limit")
    fun getRecentVehicles(limit: Int): Flow<List<RecentVehicleEntity>>

    @Query("SELECT * FROM recent_vehicle ORDER BY last_searched_date DESC")
    fun getAllRecentVehicles(): Flow<List<RecentVehicleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentVehicle(recentVehicle: RecentVehicleEntity)
}
