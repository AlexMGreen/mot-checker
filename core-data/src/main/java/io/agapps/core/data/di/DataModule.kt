package io.agapps.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.core.data.repository.VehicleDetailsRepository
import io.agapps.core.data.repository.VehicleDetailsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsVehicleDetailsRepository(vehicleDetailsRepositoryImpl: VehicleDetailsRepositoryImpl): VehicleDetailsRepository
}
