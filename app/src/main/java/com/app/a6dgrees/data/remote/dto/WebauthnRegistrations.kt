package com.app.a6dgrees.data.remote.dto

data class WebauthnRegistrations(
    val webauthn_registration_id: String,
    val domain: String,
    val user_agent: String,
    val authenticator_type: String,
    val verified: Boolean
)