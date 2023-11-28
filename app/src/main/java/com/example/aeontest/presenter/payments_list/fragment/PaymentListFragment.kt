package com.example.aeontest.presenter.payments_list.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aeontest.R
import com.example.aeontest.common.Constants
import com.example.aeontest.common.Constants.TOKEN_KEY
import com.example.aeontest.databinding.FragmentLoginBinding
import com.example.aeontest.databinding.FragmentPaymentListBinding

class PaymentListFragment : Fragment() {

    private var _binding: FragmentPaymentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPaymentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()

        binding.buttonLogout.setOnClickListener {
            val sharedPreferences =  requireActivity().getSharedPreferences(Constants.TOKEN_PREFERENCES, Context.MODE_PRIVATE)
            sharedPreferences.edit().remove(TOKEN_KEY).apply()
            requireActivity().finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}