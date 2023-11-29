package com.example.aeontest.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.aeontest.databinding.ActivityMainBinding
import com.example.aeontest.domain.TokenManager
import com.example.aeontest.presenter.login.fragment.LoginFragment
import com.example.aeontest.presenter.payments_list.fragment.PaymentListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        navigateStartScreen()
    }


    // переход на фрагмент логина
    fun navigateToLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, LoginFragment())
            .commit()
    }

    // переход на фрагмент списка
    fun navigateToPaymentListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, PaymentListFragment())
            .commit()
    }

    // стартовая навигация
    private fun navigateStartScreen() {
        if (getToken().isNullOrBlank()) {
            navigateToLoginFragment()
        } else {
            navigateToPaymentListFragment()
        }
    }

    // получение token из sharedPreferences
    private fun getToken(): String? {
        return tokenManager.getToken()
    }
}