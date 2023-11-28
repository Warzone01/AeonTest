package com.example.aeontest.presenter.login.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.aeontest.common.Constants.TOKEN_KEY
import com.example.aeontest.common.Constants.TOKEN_PREFERENCES
import com.example.aeontest.databinding.FragmentLoginBinding
import com.example.aeontest.presenter.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onResume() {
        super.onResume()
        setClicks()
        getLoginResult()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setClicks() {

        // нажатие на кнопку входа
        binding.buttonLoginLogin.setOnClickListener {
            Log.d("Вызвалось", "Нажали")
            viewModel.login(
                login = binding.etLoginUsername.text.toString(),
                password = binding.etLoginPassword.text.toString()
            )
        }
    }

    private fun getLoginResult() {

        viewModel.state.observe(viewLifecycleOwner) { resource ->
                Log.d("Вызвалось", "${
                    resource.token + resource.error + resource.isLoading
                }")

                when {
                    resource.error.isNotBlank() -> {
                        Log.d("Вызвалось", "error")
                        binding.tvLoginError.visibility = View.VISIBLE
                    }

                    !resource.token.isNullOrBlank() -> {
                        writeToken(resource.token)
                    }
                }
        }
    }

    // запись токена
    private fun writeToken(token: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }
}