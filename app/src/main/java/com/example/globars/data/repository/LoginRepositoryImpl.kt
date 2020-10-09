package com.example.globars.data.repository

import com.example.globars.data.service.ApiService
import com.example.globars.data.pojo.LoginResponse
import com.example.globars.data.pojo.request.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepositoryImpl(private val apiService: ApiService) : LoginRepository {
    override fun login(username: String, password: String, callBack: (LoginResponse?) -> Unit) {
            val apiCall = apiService.login(User(username, password))
            apiCall.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val body = response.body()
                    callBack(body)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    callBack(null)
                }

            })
    }
}