package io.agapps.feature.favouritevehicles.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouriteVehicleEntity::class], version = 1)
abstract class FavouriteVehicleDatabase : RoomDatabase() {
    abstract fun favouriteVehicleDao(): FavouriteVehicleDao
}
