package com.example.aeontest.presenter.payments_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aeontest.common.Resource
import com.example.aeontest.domain.usecase.GetPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PaymentListViewModel @Inject constructor(
    private val paymentsUseCase: GetPaymentsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PaymentListState>()
    val state: LiveData<PaymentListState> = _state

    fun getList() {
        paymentsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PaymentListState(payments = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = PaymentListState(error = result.message ?: "")
                }

                is Resource.Loading -> {
                    _state.value = PaymentListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}