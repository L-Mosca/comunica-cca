package com.example.comunicaa.screens.authentication.login

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.user.buildLoginBody
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepositoryContract) :
    BaseViewModel() {

    val loginSuccess = SingleLiveData<Unit>()
    val loginError = SingleLiveData<Unit>()

    val emailEmptyError = SingleLiveData<Unit>()
    val passwordEmptyError = SingleLiveData<Unit>()

    fun login(email: String, password: String) {
        defaultLaunch(exceptionHandler = { loginError.postValue(Unit) }) {
            if (fieldsHasData(email, password)) {
                userRepository.login(buildLoginBody(email, password))
                loginSuccess.postValue(Unit)
            }
        }
    }

    private fun fieldsHasData(email: String, password: String): Boolean {
        if (email.isEmpty()) emailEmptyError.postValue(Unit)
        if (password.isEmpty()) passwordEmptyError.postValue(Unit)

        return email.isNotEmpty() || password.isNotEmpty()
    }
}