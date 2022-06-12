package io.agapps.core.network.retrofit

import io.agapps.core.network.model.MotHistoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MotHistoryService {
    @GET("trade/vehicles/mot-tests")
    suspend fun getMotHistory(@Query("registration") registrationNumber: String): Response<List<MotHistoryDto>>
}
