package com.example.aeontest

import android.app.Application
import android.content.SharedPreferences
import com.example.aeontest.domain.TokenManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AeonApp : Application() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var tokenManager: TokenManager

    override fun onCreate() {
        super.onCreate()
        tokenManager = TokenManager(sharedPreferences)
    }
}