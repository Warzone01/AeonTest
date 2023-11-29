package com.example.aeontest.domain

import android.content.SharedPreferences
import com.example.aeontest.common.Constants.TOKEN_KEY

class TokenManager(private val sharedPreferences: SharedPreferences) {

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }
}