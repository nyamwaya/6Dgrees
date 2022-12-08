package com.app.a6dgrees.domain

import com.app.a6dgrees.data.remote.dto.*

interface SixDegreesRepository {
    suspend fun createUserWithPassword(requestBody: AuthRequest): CreateUserWithPasswordResponseDto
    suspend fun updateUser(userId: String, requestBody: UpdateUserRequest): UpdateUserResponseDto
    suspend fun sendOtp(requestBody: PhoneOtp): RequestOtpResponseDto
    suspend fun authenticateOtp(requestBody: AuthenticateOtpRequest): AuthenticateOtpResponseDto
    suspend fun login(requestBody: AuthRequest): LoginResponseDto
    suspend fun getUser(userId: String) : GetUserResponseDto
}