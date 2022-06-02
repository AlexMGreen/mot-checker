package io.agapps.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesVehicleDetailsDatabase(@ApplicationContext applicationContext: Context) = Room
        .databaseBuilder(applicationContext, VehicleDetailsDatabase::class.java, "VehicleDetailsDatabase.db")
        .build()

    @Provides
    fun providesVehicleDetailsDao(vehicleDetailsDatabase: VehicleDetailsDatabase) = vehicleDetailsDatabase.vehicleDetailsDao()
}
