package io.agapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}
