package io.agapps.core.database.model.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.agapps.core.database.model.MotTestEntity
import io.agapps.core.database.model.RfrAndCommentEntity

object VehicleEntityConverters {

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
