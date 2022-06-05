package io.agapps.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.agapps.core.model.MotTest
import io.agapps.core.model.ReasonForRejectionAndComment
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
    @TypeConverters(Converters::class)
    val motTests: List<MotTestEntity>?,
)

data class MotTestEntity(
    @ColumnInfo(name = "completed_date")
    val completedDate: String,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: String?,
    @ColumnInfo(name = "mot_test_number")
    val motTestNumber: String,
    @ColumnInfo(name = "odometer_result_type")
    val odometerResultType: String,
    @ColumnInfo(name = "odometer_unit")
    val odometerUnit: String?,
    @ColumnInfo(name = "odometer_value")
    val odometerValue: String,
    @TypeConverters(Converters::class)
    val rfrAndComments: List<RfrAndCommentEntity>,
    @ColumnInfo(name = "test_result")
    val testResult: String
)

data class RfrAndCommentEntity(
    @ColumnInfo(name = "dangerous")
    val dangerous: Boolean,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "type")
    val type: String
)

object Converters {

    private val rfrAdapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val rfrListType = Types.newParameterizedType(List::class.java, RfrAndCommentEntity::class.java)
        return@lazy moshi.adapter<List<RfrAndCommentEntity>>(rfrListType)
    }

    private val motTestAdapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val motTestListType = Types.newParameterizedType(List::class.java, MotTestEntity::class.java)
        return@lazy moshi.adapter<List<MotTestEntity>>(motTestListType)
    }

    @TypeConverter
    fun rfrAndCommentEntitiesToJson(rfrAndCommentEntities: List<RfrAndCommentEntity>?) = rfrAdapter.toJson(rfrAndCommentEntities)

    @TypeConverter
    fun rfrAndCommentEntitiesFromJson(rfrAndCommentJson: String) = rfrAdapter.fromJson(rfrAndCommentJson)

    @TypeConverter
    fun motTestEntitiesToJson(motTestEntities: List<MotTestEntity>?) = motTestAdapter.toJson(motTestEntities)

    @TypeConverter
    fun motTestEntitiesFromJson(motTestJson: String) = motTestAdapter.fromJson(motTestJson)
}

fun VehicleEntity.toDomain() = Vehicle(
    registrationNumber = registrationNumber,
    make = make,
    model = model,
    primaryColour = primaryColour,
    fuelType = fuelType,
    engineSizeCc = engineSizeCc,
    manufactureDate = manufactureDate,
    motTests = motTests?.map { it.toDomain() },
)

fun MotTestEntity.toDomain() = MotTest(
    completedDate = completedDate,
    expiryDate = expiryDate,
    motTestNumber = motTestNumber,
    odometerUnit = odometerUnit,
    odometerValue = odometerValue,
    odometerResultType = odometerResultType,
    reasonForRejectionAndComment = rfrAndComments.map { it.toDomain() },
    testResult = testResult,
)

fun RfrAndCommentEntity.toDomain() = ReasonForRejectionAndComment(
    dangerous = dangerous,
    text = text,
    type = type,
)
