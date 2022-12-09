package com.app.a6dgrees.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.a6dgrees.common.Constants.emailRegex
import com.app.a6dgrees.common.Constants.firstNameRegex
import com.app.a6dgrees.common.Constants.lastNameRegex
import com.app.a6dgrees.common.Constants.loginPasswordRegex
import com.app.a6dgrees.common.Constants.passwordRegex
import com.app.a6dgrees.common.Constants.phoneNumberRegex
import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.common.SixDegreesPreferences
import com.app.a6dgrees.data.remote.dto.*
import com.app.a6dgrees.domain.models.FormData
import com.app.a6dgrees.domain.models.LoginFormData
import com.app.a6dgrees.domain.use_cases.authenticate_otp.AuthenticateOtpUseCase
import com.app.a6dgrees.domain.use_cases.login.LoginUseCase
import com.app.a6dgrees.domain.use_cases.send_otp.SendOtpUseCase
import com.app.a6dgrees.domain.use_cases.signup.SignUpUseCase
import com.app.a6dgrees.domain.use_cases.update_user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val sendOtpUseCase: SendOtpUseCase,
    private val authenticateOtpUseCase: AuthenticateOtpUseCase,
    sharedPrefs: SixDegreesPreferences

) : ViewModel() {

    private val _formValidationResult =
        MutableStateFlow<Resource<List<Map.Entry<String, Boolean>>>>(Resource.Loading(null))
    val formValidationResult: StateFlow<Resource<List<Map.Entry<String, Boolean>>>> =
        _formValidationResult

    private val _createUserState =
        MutableStateFlow<Resource<CreateUserWithPasswordResponseDto>>(Resource.Loading(null))
    val createUserState: StateFlow<Resource<CreateUserWithPasswordResponseDto>> = _createUserState

    private val _updateUserState =
        MutableStateFlow<Resource<UpdateUserResponseDto>>(Resource.Loading(null))
    val updateUserState: StateFlow<Resource<UpdateUserResponseDto>> = _updateUserState

    private val _sendOtpState =
        MutableStateFlow<Resource<RequestOtpResponseDto>>(Resource.Loading(null))
    val sendOtpState: StateFlow<Resource<RequestOtpResponseDto>> = _sendOtpState

    private val _authenticateOtpState =
        MutableStateFlow<Resource<AuthenticateOtpResponseDto>>(Resource.Loading(null))
    val authenticateOtpState: StateFlow<Resource<AuthenticateOtpResponseDto>> =
        _authenticateOtpState

    val mSharedPrefs = sharedPrefs

    private fun validateFormData(data: FormData): Map<String, Boolean> {
        return mapOf(
            "firstName" to firstNameRegex.matches(data.firstName),
            "lastName" to lastNameRegex.matches(data.lastName),
            "email" to emailRegex.matches(data.email),
            "phoneNumber" to phoneNumberRegex.matches(data.phoneNumber),
            "password" to (passwordRegex.matches(data.password) && data.password == data.confirmPassword)
        )
    }

    //create it's own sealed class the returns and error when form data is failed
    fun processFormData(data: FormData) {
        val validationResults = validateFormData(data)
        if (validationResults.all { it.value }) {
            signUpUser(
                LoginRequest(
                    email = data.email,
                    password = data.password
                )
            )
        } else {
            val failedFields = validationResults.entries.filter { !it.value }
            if (failedFields.isNotEmpty()) {
                _formValidationResult.value = Resource.Error(failedFields.joinToString())
            }
        }
    }

    private fun signUpUser(
        createUserRequest: LoginRequest,
    ) {
        signUpUseCase(createUserRequest).onEach { result ->
            _createUserState.value = result
        }.launchIn(viewModelScope)
    }

    fun updateUser(userId: String, updateUserRequest: UpdateUserRequest) {
        updateUserUseCase(userId, updateUserRequest).onEach { result ->
            _updateUserState.value = result
        }.launchIn(viewModelScope)
    }

    fun sendOtp(sendOtpRequest: PhoneOtp) {
        sendOtpUseCase(sendOtpRequest).onEach { result ->
            _sendOtpState.value = result
        }.launchIn(viewModelScope)
    }

    fun authenticateOtp(authenticateOtpRequest: AuthenticateOtpRequest) {
        authenticateOtpUseCase(authenticateOtpRequest).onEach { result ->
            _authenticateOtpState.value = result
        }.launchIn(viewModelScope)
    }




}