package com.example.aeontest.presenter.payments_list.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aeontest.databinding.FragmentPaymentListBinding
import com.example.aeontest.domain.TokenManager
import com.example.aeontest.presenter.MainActivity
import com.example.aeontest.presenter.payments_list.PaymentListViewModel
import com.example.aeontest.presenter.payments_list.adapter.PaymentsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentListFragment : Fragment() {

    private val viewModel: PaymentListViewModel by viewModels()
    private var _binding: FragmentPaymentListBinding? = null

    @Inject
    lateinit var tokenManager: TokenManager
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPaymentListBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onResume() {
        super.onResume()
        setClicks()
        viewModel.getList()
        getPaymentListResult()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // обрабатывает нажатия
    private fun setClicks() {
        binding.buttonLogout.setOnClickListener {
            tokenManager.clearToken()
            (activity as? MainActivity)?.navigateToLoginFragment()
        }
    }

    // обработка результатов ответа
    private fun getPaymentListResult() {
        viewModel.state.observe(viewLifecycleOwner) { resource ->
            when {
                resource.error.isNotBlank() -> {
                    binding.tvPaymentsError.text = resource.error
                    binding.tvPaymentsError.visibility = View.VISIBLE
                }

                resource.payments.isNotEmpty() -> {
                    val adapter = PaymentsAdapter(resource.payments)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}