package com.example.aeontest.domain.model

import java.time.temporal.TemporalAmount

data class Payment(
    val amount: Double,
    val created: Int,
    val id: Int,
    val title: String
)
