package io.agapps.core.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.core.network.retrofit.RetrofitMotHistoryNetworkSource

@Module
@InstallIn(SingletonComponent::class)
interface MotHistoryNetworkSourceModule {

    @Binds
    fun bindsMotHistoryNetworkSource(motHistoryNetworkSource: RetrofitMotHistoryNetworkSource): MotHistoryNetworkSource
}
