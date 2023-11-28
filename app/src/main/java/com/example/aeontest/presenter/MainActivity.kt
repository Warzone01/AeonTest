package com.example.aeontest.presenter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.aeontest.common.Constants.TOKEN_KEY
import com.example.aeontest.common.Constants.TOKEN_PREFERENCES
import com.example.aeontest.databinding.ActivityMainBinding
import com.example.aeontest.presenter.login.fragment.LoginFragment
import com.example.aeontest.presenter.payments_list.fragment.PaymentListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        navigate()
    }


    // навигация между фрагментами
    private fun navigate() {
        Log.d("Вызвалось", getToken().toString())

        supportFragmentManager.beginTransaction()
            .replace(
                binding.mainContainer.id,
                if (getToken().isNullOrBlank()) LoginFragment() else PaymentListFragment()
            )
            .commit()
    }

    private fun getToken(): String? {
        val sharedPreferences = this.getSharedPreferences(TOKEN_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}