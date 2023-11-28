package com.example.aeontest.di

import com.example.aeontest.common.Constants.BASE_URL
import com.example.aeontest.common.Constants.LOGIN
import com.example.aeontest.data.remote.AuthInterceptor
import com.example.aeontest.data.remote.EasyPayApi
import com.example.aeontest.data.repository.EasyPayRepositoryImpl
import com.example.aeontest.domain.repository.EasyPayRepository
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

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor("token", LOGIN)
    }

    @Provides
    @Singleton
    fun provideEasyPayApi(): EasyPayApi {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(EasyPayApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEasyPayRepository(api: EasyPayApi): EasyPayRepository {
        return EasyPayRepositoryImpl(api)
    }
}