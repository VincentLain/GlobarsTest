package com.example.globars.data.repository

import com.example.globars.data.pojo.LoginResponse

interface LoginRepository {
    fun login(username: String, password: String, callBack: (LoginResponse?) -> Unit)
}