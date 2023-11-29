package com.example.aeontest.domain.usecase

import com.example.aeontest.common.Resource
import com.example.aeontest.data.remote.dto.toPayment
import com.example.aeontest.domain.model.Payment
import com.example.aeontest.domain.repository.EasyPayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPaymentsUseCase @Inject constructor(
    private val repository: EasyPayRepository
) {
    operator fun invoke(): Flow<Resource<List<Payment>>> = flow {
        try {
            emit(Resource.Loading<List<Payment>>())
            val payments =
                repository.getPayments().body()?.response?.map { it.toPayment() } ?: emptyList()
            emit(Resource.Success<List<Payment>>(payments))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Payment>>(e.localizedMessage ?: "Unknown error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Payment>>(e.localizedMessage ?: "Unknown error"))
        }
    }
}