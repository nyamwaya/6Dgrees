package com.app.a6dgrees.data.remote.dto

data class GetUserResponseDto(
    val status_code: Int,
    val request_id: String,
    val user_id: String,
    val names: Name,
    val trusted_metadata: TrustedMetadata,
    val untrusted_metadata: UntrustedMetadata,
    val emails: List<Email>,
    val phone_numbers: List<PhoneNumber>,
    val providers: List<Providers>,
    val webauthn_registrations: List<WebauthnRegistrations>,
    val biometric_registrations: List<BiometricRegistrations>,
    val totps: List<Ttotps>,
    val crypto_wallets: List<CryptoWallet>,
    val password: Password,
    val created_at: String,
    val status: String,
)

