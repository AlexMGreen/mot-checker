package io.agapps.core.network.retrofit

import com.github.ajalt.timberkt.Timber
import io.agapps.common.result.Result
import io.agapps.core.network.MotHistoryNetworkSource
import io.agapps.core.network.model.MotHistoryDto
import java.io.IOException
import javax.inject.Inject

class RetrofitMotHistoryNetworkSource @Inject constructor(
    private val motHistoryService: MotHistoryService
) : MotHistoryNetworkSource {

    override suspend fun getMotHistory(registrationNumber: String): Result<MotHistoryDto> {
        Timber.d { "Calling MotHistoryService for $registrationNumber" }
        val response = motHistoryService.getMotHistory(registrationNumber)
        return if (response.isSuccessful) {
            Timber.d { "MotHistoryService response successful: $response" }
            Result.Success(data = response.body()!!.first())
        } else {
            Timber.e { "MotHistoryService response error: $response" }
            Result.Error(IOException(response.errorBody().toString()))
        }
    }

}
