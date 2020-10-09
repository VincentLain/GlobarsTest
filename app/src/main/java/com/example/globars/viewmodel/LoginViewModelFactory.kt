package com.example.globars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.globars.data.repository.LoginRepositoryImpl
import com.example.globars.data.service.ApiService

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepositoryImpl(
                    apiService = ApiService.instance
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}