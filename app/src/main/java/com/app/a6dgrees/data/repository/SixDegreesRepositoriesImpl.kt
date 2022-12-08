package com.app.a6dgrees.data.repository

import com.app.a6dgrees.data.remote.StytchApi
import com.app.a6dgrees.data.remote.dto.*
import com.app.a6dgrees.domain.SixDegreesRepository
import javax.inject.Inject

class SixDegreesRepositoriesImpl @Inject constructor(
    private val api: StytchApi
) : SixDegreesRepository {

    override suspend fun createUserWithPassword(requestBody: AuthRequest): CreateUserWithPasswordResponseDto {
        return api.signUp(requestBody)
    }

    override suspend fun updateUser(
        userId: String,
        requestBody: UpdateUserRequest
    ): UpdateUserResponseDto {
        return api.updateUser(userId, requestBody)
    }

    override suspend fun sendOtp(requestBody: PhoneOtp): RequestOtpResponseDto {
        return api.sendOtp(requestBody)
    }

    override suspend fun authenticateOtp(requestBody: AuthenticateOtpRequest): AuthenticateOtpResponseDto {
        return api.authenticateOtp(requestBody)
    }

    override suspend fun login(requestBody: AuthRequest): LoginResponseDto {
        return api.login(requestBody)
    }

    override suspend fun getUser(userId: String): GetUserResponseDto {
        return api.getUser(userId)
    }

}