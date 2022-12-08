package com.app.a6dgrees.data.remote

import com.app.a6dgrees.data.remote.dto.*
import retrofit2.http.*

interface StytchApi {
    // Create a user with password
    @POST("passwords")
    suspend fun signUp(
        @Body requestBody: AuthRequest
    ): CreateUserWithPasswordResponseDto

    @POST("passwords/authenticate")
    suspend fun login(
        @Body requestBody: AuthRequest
    ) : LoginResponseDto

    @PUT("users/{userId}")
    suspend fun updateUser(
        @Path("userId") string: String,
        @Body requestBody: UpdateUserRequest
    ): UpdateUserResponseDto

    @POST("otps/sms/send")
    suspend fun sendOtp(
        @Body requestBody: PhoneOtp
    ): RequestOtpResponseDto

    @POST("otps/authenticate")
    suspend fun authenticateOtp(
        @Body requestBody: AuthenticateOtpRequest
    ): AuthenticateOtpResponseDto

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") string: String
    ) : GetUserResponseDto

}