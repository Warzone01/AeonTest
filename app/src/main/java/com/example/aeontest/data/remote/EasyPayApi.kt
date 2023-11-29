package com.example.aeontest.data.remote

import com.example.aeontest.data.remote.dto.PaymentsResponse
import com.example.aeontest.data.remote.dto.SuccessResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EasyPayApi {

    @FormUrlEncoded
    @POST("api-test/login")
    suspend fun login(
        @Field("login") login: String,
        @Field("password") password: String
    ): Response<SuccessResponse>

    @GET("api-test/payments")
    suspend fun getPayments(): Response<PaymentsResponse>
}