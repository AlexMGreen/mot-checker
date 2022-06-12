package io.agapps.core.network.retrofit

import com.github.ajalt.timberkt.Timber
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.agapps.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.d { message }
    }.apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

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
        .addInterceptor(loggingInterceptor)
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
