package com.example.aeontest.data.remote

import com.example.aeontest.common.Constants.APP_KEY
import com.example.aeontest.common.Constants.LOGIN
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authToken: String,
    private val request: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("app-key", APP_KEY)
            .header("v", "1")

        when (request) {
            LOGIN -> {
                requestBuilder.header("token", authToken)
            }
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}