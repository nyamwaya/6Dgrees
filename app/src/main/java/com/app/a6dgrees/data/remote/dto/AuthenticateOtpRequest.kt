package com.app.a6dgrees.data.remote.dto

data class AuthenticateOtpRequest(
    val methodId: String,
    val otpCode: String
)
