package com.example.aeontest.data.remote

import com.example.aeontest.common.Constants.APP_KEY
import com.example.aeontest.common.Constants.BACKEND_CODE_VERSION
import com.example.aeontest.common.Constants.TOKEN
import com.example.aeontest.domain.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val request: String
) : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("app-key", APP_KEY)
            .header("v", BACKEND_CODE_VERSION)

        when (request) {
            TOKEN -> {
                requestBuilder.header("token", getToken() ?: "")
            }
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun getToken(): String? {
        return tokenManager.getToken()
    }
}