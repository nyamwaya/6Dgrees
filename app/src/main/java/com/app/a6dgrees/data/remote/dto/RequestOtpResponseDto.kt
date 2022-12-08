package com.app.a6dgrees.data.remote.dto


data class RequestOtpResponseDto(
    val phone_id: String,
    val request_id: String,
    val status_code: Int,
    val user_id: String
)
