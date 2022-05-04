package io.agapps.data.vehicledetails

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.domain.vehicledetails.VehicleDetailsRepository

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
abstract class VehicleDetailsModule {

    @Binds
    abstract fun bindsVehicleDetailsRepository(vehicleDetailsRepositoryImpl: VehicleDetailsRepositoryImpl): VehicleDetailsRepository
}
