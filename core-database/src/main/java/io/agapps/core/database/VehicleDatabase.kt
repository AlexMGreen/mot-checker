package io.agapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.agapps.core.database.dao.VehicleDao
import io.agapps.core.database.model.VehicleEntity
import io.agapps.core.database.model.converters.VehicleEntityConverters

@Database(entities = [VehicleEntity::class], version = 1)
@TypeConverters(VehicleEntityConverters::class)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}
