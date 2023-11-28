package com.example.aeontest.data.remote.dto

data class SuccessResponse(
    val success: Boolean,
    val response: ResponseData?,
    val error: ErrorData?
)

data class ErrorResponse(
    val success: Boolean,
    val error: ErrorData?
)

data class ResponseData(
    val token: String
)

data class ErrorData(
    val error_code: Int,
    val error_msg: String
)