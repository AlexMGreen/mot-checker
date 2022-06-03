package io.agapps.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.core.data.repository.VehicleRepository
import io.agapps.core.data.repository.VehicleRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsVehicleRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository
}
