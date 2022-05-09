package io.agapps.data.vehicledetails

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.agapps.domain.vehicledetails.VehicleDetailsRepository

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
abstract class VehicleDetailsModule {

    @Binds
    abstract fun bindsVehicleDetailsRepository(vehicleDetailsRepositoryImpl: VehicleDetailsRepositoryImpl): VehicleDetailsRepository

    companion object {
        @Provides
        fun providesVehicleDetailsDatabase(@ApplicationContext applicationContext: Context) = Room
            .databaseBuilder(applicationContext, VehicleDetailsDatabase::class.java, "VehicleDetailsDatabase.db")
            .build()

        @Provides
        fun providesVehicleDetailsDao(vehicleDetailsDatabase: VehicleDetailsDatabase) = vehicleDetailsDatabase.vehicleDetailsDao()
    }
}
