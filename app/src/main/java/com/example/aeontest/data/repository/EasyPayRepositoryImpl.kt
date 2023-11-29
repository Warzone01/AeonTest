package com.example.aeontest.data.repository

import com.example.aeontest.data.remote.EasyPayApi
import com.example.aeontest.data.remote.dto.PaymentsResponse
import com.example.aeontest.data.remote.dto.SuccessResponse
import com.example.aeontest.domain.repository.EasyPayRepository
import retrofit2.Response
import javax.inject.Inject

class EasyPayRepositoryImpl @Inject constructor(
    private val api: EasyPayApi,
) : EasyPayRepository {
    override suspend fun login(login: String, password: String): Response<SuccessResponse> {
        return api.login(login, password)
    }

    override suspend fun getPayments(): Response<PaymentsResponse> {
        return api.getPayments()
    }

}