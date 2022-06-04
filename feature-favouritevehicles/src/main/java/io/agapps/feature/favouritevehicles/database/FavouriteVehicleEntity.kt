package io.agapps.feature.favouritevehicles.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_vehicle")
data class FavouriteVehicleEntity(
    @PrimaryKey @ColumnInfo(name = "vehicle_registration_number")
    val vehicleRegistrationNumber: String,
)
