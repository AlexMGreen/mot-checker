package io.agapps.feature.recentvehicles

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.agapps.feature.recentvehicles.data.RecentVehicleRepository
import io.agapps.feature.recentvehicles.data.RecentVehicleRepositoryImpl
import io.agapps.feature.recentvehicles.database.RecentVehicleDatabase

@Module
@InstallIn(SingletonComponent::class)
interface RecentVehicleModule {

    @Binds
    fun bindsRecentVehicleRepository(recentVehicleRepositoryImpl: RecentVehicleRepositoryImpl): RecentVehicleRepository

    companion object {

        @Provides
        fun providesRecentVehicleDatabase(@ApplicationContext applicationContext: Context) = Room
            .databaseBuilder(applicationContext, RecentVehicleDatabase::class.java, "RecentVehicleDatabase.db")
            .build()


        @Provides
        fun providesRecentVehicleDao(recentVehicleDatabase: RecentVehicleDatabase) = recentVehicleDatabase.recentVehicleDao()
    }
}

