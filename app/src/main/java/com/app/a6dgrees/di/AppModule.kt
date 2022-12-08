package com.app.a6dgrees.di

import com.app.a6dgrees.common.Constants
import com.app.a6dgrees.data.remote.BasicAuthInterceptor
import com.app.a6dgrees.data.remote.StytchApi
import com.app.a6dgrees.data.repository.SixDegreesRepositoriesImpl
import com.app.a6dgrees.domain.SixDegreesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    private val client = OkHttpClient.Builder()
        .addInterceptor(
            BasicAuthInterceptor(
                Constants.USERNAME,
                Constants.PASSWORD
            )
        )
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideStytchApi(): StytchApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_DEV_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(StytchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStytechRepository(api: StytchApi): SixDegreesRepository {
        return SixDegreesRepositoriesImpl(api)
    }
}