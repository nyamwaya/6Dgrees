package com.app.a6dgrees.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.a6dgrees.common.Constants
import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.common.SixDegreesPreferences
import com.app.a6dgrees.data.remote.dto.LoginRequest
import com.app.a6dgrees.data.remote.dto.LoginResponseDto
import com.app.a6dgrees.domain.models.LoginFormData
import com.app.a6dgrees.domain.use_cases.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    sharedPrefs: SixDegreesPreferences,

) : ViewModel() {
    val mSharedPrefs = sharedPrefs

    private val _loginUserState =
        MutableStateFlow<Resource<LoginResponseDto>>(Resource.Loading(null))
    val loginUserState: StateFlow<Resource<LoginResponseDto>> = _loginUserState

    private val _loginFormValidationResult =
        MutableStateFlow<Resource<List<Map.Entry<String, Boolean>>>>(Resource.Loading(null))
    val loginFormValidationResult: StateFlow<Resource<List<Map.Entry<String, Boolean>>>> =
        _loginFormValidationResult

    private fun validateLoginData(data: LoginFormData): Map<String, Boolean> {
        return mapOf(
            "email" to Constants.emailRegex.matches(data.email),
            "password" to Constants.loginPasswordRegex.matches(data.password),
        )
    }

    //create it's own sealed class the returns and error when form data is failed
    fun processLoginFormData(formData: LoginFormData) {
        val validationResults = validateLoginData(formData)
        if (validationResults.all { it.value }) {
            loginUser(
                LoginRequest(
                    email = formData.email,
                    password = formData.password
                )
            )
        } else {
            val failedFields = validationResults.entries.filter { !it.value }
            if (failedFields.isNotEmpty()) _loginFormValidationResult.value =
                Resource.Error(failedFields.joinToString())
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        loginUseCase(loginRequest).onEach { result ->
            _loginUserState.value = result
        }.launchIn(viewModelScope)
    }

}