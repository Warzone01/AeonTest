package com.example.aeontest.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.aeontest.common.Constants
import com.example.aeontest.common.Constants.BASE_URL
import com.example.aeontest.common.Constants.TOKEN
import com.example.aeontest.data.remote.AuthInterceptor
import com.example.aeontest.data.remote.EasyPayApi
import com.example.aeontest.data.repository.EasyPayRepositoryImpl
import com.example.aeontest.domain.TokenManager
import com.example.aeontest.domain.repository.EasyPayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(Constants.TOKEN_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(TOKEN).apply {
            this.tokenManager = tokenManager
        }
    }

    @Provides
    @Singleton
    fun provideEasyPayApi(okHttpClient: OkHttpClient): EasyPayApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(EasyPayApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEasyPayRepository(api: EasyPayApi): EasyPayRepository {
        return EasyPayRepositoryImpl(api)
    }
}