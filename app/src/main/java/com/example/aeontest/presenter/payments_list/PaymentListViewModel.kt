package com.example.aeontest.presenter.payments_list

import androidx.lifecycle.ViewModel
import com.example.aeontest.domain.usecase.GetPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentListViewModel @Inject constructor(
    private val paymentsUseCase: GetPaymentsUseCase
): ViewModel() {


}