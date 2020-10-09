package com.example.globars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.globars.data.repository.TrackingRepository
import com.example.globars.data.service.ApiService

class TrackingViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrackingViewModel::class.java)) {
            return TrackingViewModel(
                trackingRepository = TrackingRepository(
                    apiService = ApiService.instance
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}