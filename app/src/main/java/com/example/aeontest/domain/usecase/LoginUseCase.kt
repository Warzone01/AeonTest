package com.example.aeontest.domain.usecase

import com.example.aeontest.common.Resource
import com.example.aeontest.domain.repository.EasyPayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.example.aeontest.domain.model.UserToken

class LoginUseCase @Inject constructor(
    private val repository: EasyPayRepository
) {

    operator fun invoke(login: String, password: String): Flow<Resource<UserToken>> = flow {
        try {
            emit(Resource.Loading<UserToken>())
            val response = repository.login(login, password).body()

            if (response?.success == true) {
                val token = response.response?.token ?: ""
                emit(Resource.Success<UserToken>(UserToken(token)))
            } else {
                val error = response?.error?.error_msg ?: "Unknown error"
                emit(Resource.Error<UserToken>(error))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<UserToken>(e.localizedMessage ?: ""))
        } catch (e: IOException) {
            emit(Resource.Error<UserToken>(e.localizedMessage ?: ""))
        }
    }
}