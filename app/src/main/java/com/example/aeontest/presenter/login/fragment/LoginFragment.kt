package com.example.aeontest.presenter.login.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.aeontest.R
import com.example.aeontest.common.Constants
import com.example.aeontest.databinding.FragmentLoginBinding
import com.example.aeontest.domain.TokenManager
import com.example.aeontest.presenter.MainActivity
import com.example.aeontest.presenter.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var tokenManager: TokenManager

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

    // Сохранение токена
    private fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }

    // устанавливает клики
    private fun setClicks() {

        // нажатие на кнопку входа
        binding.buttonLoginLogin.setOnClickListener {
            if (binding.etLoginPassword.text.isNullOrBlank()
                || binding.etLoginUsername.text.isNullOrBlank()) {
                setError(getString(R.string.empty_field))
            } else {
                viewModel.login(
                    login = binding.etLoginUsername.text.toString(),
                    password = binding.etLoginPassword.text.toString()
                )
            }
        }
    }

    // обработка результатов запроса
    private fun getLoginResult() {
        viewModel.state.observe(viewLifecycleOwner) { resource ->

            when {
                resource.error.isNotBlank() -> {
                    if (resource.error.contains(Constants.UNABLE_TO_RESOLVE_HOST)) {
                        setError(getString(R.string.connection_failed))
                    } else {
                        setError(getString(R.string.incorrect_login_or_password))
                    }
                }

                !resource.token.isNullOrBlank() -> {
                    saveToken(resource.token)
                    (activity as? MainActivity)?.navigateToPaymentListFragment()
                }
            }
        }
    }

    // выставляет состояние ошибки
    private fun setError(text: String) {
        binding.tvLoginError.visibility = View.VISIBLE
        binding.tvLoginError.text = text
        binding.etLoginUsernameLayout.boxStrokeColor = ContextCompat.getColor(
            requireContext(),
            R.color.red
        )
        binding.etLoginPasswordLayout.boxStrokeColor = ContextCompat.getColor(
            requireContext(),
            R.color.red
        )
        binding.etLoginUsernameLayout.requestFocus()
    }
}