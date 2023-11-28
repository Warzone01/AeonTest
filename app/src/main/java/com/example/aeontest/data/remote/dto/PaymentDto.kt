package com.example.aeontest.data.remote.dto

import com.example.aeontest.domain.model.Payment

data class PaymentDto(
    val amount: Double,
    val created: Int,
    val id: Int,
    val title: String
)

fun PaymentDto.toPayment(): Payment {
    return Payment(
        amount = amount,
        created = created,
        id = id,
        title = title
    )
}