package io.agapps.feature.recentvehicles.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "recent_vehicle")
data class RecentVehicleEntity(
    @PrimaryKey @ColumnInfo(name = "vehicle_registration_number")
    val vehicleRegistrationNumber: String,
    @ColumnInfo(name = "last_searched_date")
    val lastSearchedDate: Long? = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
)
