package io.agapps.core.network

import io.agapps.common.result.Result
import io.agapps.core.network.model.MotHistoryDto

interface MotHistoryNetworkSource {
    suspend fun getMotHistory(registrationNumber: String): Result<MotHistoryDto>
}
