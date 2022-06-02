package io.agapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.agapps.core.database.dao.VehicleDetailsDao
import io.agapps.core.database.model.VehicleDetailsEntity

@Database(entities = [VehicleDetailsEntity::class], version = 1)
abstract class VehicleDetailsDatabase : RoomDatabase() {
    abstract fun vehicleDetailsDao(): VehicleDetailsDao
}
