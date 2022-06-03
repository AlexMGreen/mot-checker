package io.agapps.feature.recentvehicles.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecentVehicleEntity::class], version = 1)
abstract class RecentVehicleDatabase : RoomDatabase() {
    abstract fun recentVehicleDao(): RecentVehicleDao
}
