package com.app.a6dgrees.data.remote.dto

data class CryptoWallet(
    val crypto_wallet_address: String,
    val crypto_wallet_id: String,
    val crypto_wallet_type: String,
    val verified: Boolean
)