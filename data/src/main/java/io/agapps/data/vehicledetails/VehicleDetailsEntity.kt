package io.agapps.data.vehicledetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_details")
data class VehicleDetailsEntity(
    @PrimaryKey @ColumnInfo(name = "registration_number") val registrationNumber: String,
    @ColumnInfo(name = "make") val make: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "primary_colour") val primaryColour: String,
    @ColumnInfo(name = "fuel_type") val fuelType: String,
    @ColumnInfo(name = "engine_size_cc") val engineSizeCc: String?,
    @ColumnInfo(name = "manufacture_date") val manufactureDate: String,
)

// TODO: Persist MOT Tests
