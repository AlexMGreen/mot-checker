package io.agapps.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.agapps.core.model.Vehicle

@Entity(tableName = "vehicle")
data class VehicleEntity(
    @PrimaryKey @ColumnInfo(name = "registration_number") val registrationNumber: String,
    @ColumnInfo(name = "make") val make: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "primary_colour") val primaryColour: String,
    @ColumnInfo(name = "fuel_type") val fuelType: String,
    @ColumnInfo(name = "engine_size_cc") val engineSizeCc: String?,
    @ColumnInfo(name = "manufacture_date") val manufactureDate: String,
)

// TODO: Persist MOT Tests

fun VehicleEntity.toDomain() = Vehicle(
    registrationNumber = registrationNumber,
    make = make,
    model = model,
    primaryColour = primaryColour,
    fuelType = fuelType,
    engineSizeCc = engineSizeCc,
    manufactureDate = manufactureDate,
    // TODO: Persist MOT Tests
    motTests = emptyList(),
)
