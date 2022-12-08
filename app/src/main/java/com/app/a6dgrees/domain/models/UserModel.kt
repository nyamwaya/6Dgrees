package com.app.a6dgrees.domain.models

import android.os.Parcelable
import com.app.a6dgrees.data.remote.dto.Email
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val email: Email,
    val isPhoneNumberVerified: Boolean,
    val userId: String
) : Parcelable