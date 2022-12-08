package com.app.a6dgrees.data.remote.dto

data class User(
    val biometric_registrations: List<Any>,
    val created_at: String,
    val crypto_wallets: List<Any>,
    val emails: List<Email>,
    val name: Name,
    val password: Password,
    val phone_numbers: List<PhoneNumber>,
    val providers: List<Any>,
    val status: String,
    val totps: List<Any>,
    val trusted_metadata: TrustedMetadata,
    val untrusted_metadata: UntrustedMetadata,
    val user_id: String,
    val webauthn_registrations: List<Any>
)