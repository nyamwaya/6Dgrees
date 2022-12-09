package com.app.a6dgrees.domain

import com.app.a6dgrees.data.remote.dto.*

interface SixDegreesRepository {
    suspend fun createUserWithPassword(requestBody: LoginRequest): CreateUserWithPasswordResponseDto
    suspend fun updateUser(userId: String, requestBody: UpdateUserRequest): UpdateUserResponseDto
    suspend fun sendOtp(requestBody: PhoneOtp): RequestOtpResponseDto
    suspend fun authenticateOtp(requestBody: AuthenticateOtpRequest): AuthenticateOtpResponseDto
    suspend fun login(requestBody: LoginRequest): LoginResponseDto
    suspend fun getUser(userId: String) : GetUserResponseDto
}