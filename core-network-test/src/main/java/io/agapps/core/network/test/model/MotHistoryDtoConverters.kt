package io.agapps.core.network.test.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.agapps.core.network.model.MotHistoryDto

object MotHistoryDtoConverters {

    private val motHistoryAdapter by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(MotHistoryDto::class.java)
    }

    fun motHistoryDtoToJson(motHistoryDto: MotHistoryDto?) = motHistoryAdapter.toJson(motHistoryDto)

    fun motHistoryDtoFromJson(motHistoryJson: String) = motHistoryAdapter.fromJson(motHistoryJson)
}

