package com.example.globars.data.repository

import android.util.Log
import com.example.globars.data.pojo.SessionsResponse
import com.example.globars.data.pojo.units.UnitsResponse
import com.example.globars.data.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackingRepository(private val apiService: ApiService) {

    fun getTrackingSessions(token: String, callBack: (SessionsResponse?) -> Unit) {
        val call = apiService.getIdSession(token)
        call.enqueue(object : Callback<SessionsResponse> {
            override fun onFailure(call: Call<SessionsResponse>, t: Throwable) {
                callBack(null)
            }

            override fun onResponse(
                call: Call<SessionsResponse>,
                response: Response<SessionsResponse>
            ) {
                val body = response?.body()
                callBack(body)
            }
        })

    }

    fun getTrackingUnitById(token: String, id: String, callBack: (UnitsResponse?) -> Unit) {
        val call = apiService.getUnits(token, id, true)
        call.enqueue(object : Callback<UnitsResponse> {
            override fun onFailure(call: Call<UnitsResponse>, t: Throwable) {
                callBack(null)
            }

            override fun onResponse(call: Call<UnitsResponse>, response: Response<UnitsResponse>) {
                val body = response?.body()
                callBack(body)
            }
        })
    }
}