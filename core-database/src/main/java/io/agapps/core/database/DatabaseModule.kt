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
    fun providesVehicleDatabase(@ApplicationContext applicationContext: Context) = Room
        .databaseBuilder(applicationContext, VehicleDatabase::class.java, "VehicleDatabase.db")
        .build()

    @Provides
    fun providesVehicleDao(vehicleDatabase: VehicleDatabase) = vehicleDatabase.vehicleDao()
}
