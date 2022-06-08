package io.agapps.feature.favouritevehicles

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepository
import io.agapps.feature.favouritevehicles.data.FavouriteVehicleRepositoryImpl
import io.agapps.feature.favouritevehicles.database.FavouriteVehicleDatabase

@Module
@InstallIn(SingletonComponent::class)
interface FavouriteVehicleModule {

    @Binds
    fun bindsFavouriteVehicleRepository(favouriteVehicleRepositoryImpl: FavouriteVehicleRepositoryImpl): FavouriteVehicleRepository

    companion object {

        @Provides
        fun providesFavouriteVehicleDatabase(@ApplicationContext applicationContext: Context) = Room
            .databaseBuilder(applicationContext, FavouriteVehicleDatabase::class.java, "FavouriteVehicleDatabase.db")
            .build()


        @Provides
        fun providesFavouriteVehicleDao(favouriteVehicleDatabase: FavouriteVehicleDatabase) = favouriteVehicleDatabase.favouriteVehicleDao()
    }
}

