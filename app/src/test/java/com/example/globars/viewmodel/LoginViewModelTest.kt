package com.example.globars.viewmodel

import com.example.globars.data.repository.LoginRepository
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    lateinit var repository: LoginRepository

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(repository)
    }
}