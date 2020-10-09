package com.example.globars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.globars.data.repository.TrackingRepository
import com.example.globars.ui.maps.TrackingSessionsResult
import com.example.globars.ui.maps.TrackingUnitsResult

class TrackingViewModel(private val trackingRepository: TrackingRepository) : ViewModel() {

    private val _traсkingSessionsResult = MutableLiveData<TrackingSessionsResult>()
    val traсkingSessionsResult: LiveData<TrackingSessionsResult> = _traсkingSessionsResult

    private val _trackingUnitsResult = MutableLiveData<TrackingUnitsResult>()
    val trackingUnitsResult: LiveData<TrackingUnitsResult> = _trackingUnitsResult

    fun getTrackingSessionsId(token: String) {
        trackingRepository.getTrackingSessions(token) { response ->
            if(response != null && response.success) {
                _traсkingSessionsResult.value = TrackingSessionsResult(success = response.data[0].id)
            } else {
                _traсkingSessionsResult.value = TrackingSessionsResult(error = "error:")
            }
        }
    }

    fun getUnitsById(token: String, sessionId: String) {
        trackingRepository.getTrackingUnitById(token, sessionId) {response ->
            if (response != null && response.success) {
                _trackingUnitsResult.value = TrackingUnitsResult(success = response.data)
            } else {
                _trackingUnitsResult.value = TrackingUnitsResult(error = "error")
            }
        }
    }
}