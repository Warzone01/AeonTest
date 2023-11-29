package com.example.aeontest.data.remote.dto

import com.example.aeontest.domain.model.Payment

data class PaymentDto(
    val id: Int,
    val title: String,
    val amount: Any?,
    val created: Int
)

data class PaymentsResponse(
    val success: String,
    val response: List<PaymentDto>?,
    val error: ErrorData?
)

fun PaymentDto.toPayment(): Payment {
    return Payment(
        amount = amount,
        created = created,
        id = id,
        title = title
    )
}