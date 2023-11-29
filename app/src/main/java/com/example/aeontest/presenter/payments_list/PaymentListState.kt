package com.example.aeontest.presenter.payments_list

import com.example.aeontest.domain.model.Payment

data class PaymentListState(
    val isLoading: Boolean = false,
    val payments: List<Payment> = emptyList(),
    val error: String = ""
)

