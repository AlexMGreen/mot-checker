package io.agapps.data.vehicledetails

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VehicleDetailsEntity::class], version = 1)
abstract class VehicleDetailsDatabase : RoomDatabase() {
    abstract fun vehicleDetailsDao(): VehicleDetailsDao
}
