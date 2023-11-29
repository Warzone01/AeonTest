package com.example.aeontest.domain.repository

import com.example.aeontest.data.remote.dto.PaymentsResponse
import com.example.aeontest.data.remote.dto.SuccessResponse
import retrofit2.Response

interface EasyPayRepository {

    suspend fun login(login: String, password: String): Response<SuccessResponse>
    suspend fun getPayments(): Response<PaymentsResponse>
}