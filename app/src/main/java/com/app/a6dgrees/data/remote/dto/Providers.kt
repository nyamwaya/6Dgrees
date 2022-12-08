package com.app.a6dgrees.data.remote.dto


data class Providers(
    val oauth_user_registration_id: String,
    val provider_subject: String,
    val provider_type: String,
    val profile_picture_url: String,
    val locale: String
)