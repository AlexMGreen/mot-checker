package io.agapps.data.mothistory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MotHistoryModule {

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json+v6")
            .addHeader("x-api-key", BuildConfig.MOT_API_KEY)
            .build()

        chain.proceed(request)
    }

    private val motClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    // TODO: Allow remote config of base URL
    @Provides
    fun providesMotHistoryService(): MotHistoryService = Retrofit.Builder()
        .client(motClient)
        .baseUrl("https://beta.check-mot.service.gov.uk")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MotHistoryService::class.java)
}
