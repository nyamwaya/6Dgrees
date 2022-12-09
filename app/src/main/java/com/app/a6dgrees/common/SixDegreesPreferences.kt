package com.app.a6dgrees.common

import android.content.Context
import android.content.SharedPreferences
import com.app.a6dgrees.common.Constants.PREFS_IS_USER_PHONE_VERIFIED
import com.app.a6dgrees.common.Constants.PREFS_USER_ID
import com.app.a6dgrees.common.Constants.PREFS_USER_MODEL
import com.app.a6dgrees.common.Constants.SHARED_PREFS_NAME
import com.app.a6dgrees.domain.models.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SixDegreesPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getUserModel(): String? {
        return prefs.getString(PREFS_USER_MODEL, "")
    }

    fun setUserModel(userModel: UserModel) {
        prefs.edit().putString(PREFS_USER_MODEL, toJson(userModel)).apply()
    }

    fun getUserId(): String? {
        return prefs.getString(PREFS_USER_ID, "")
    }

    fun setUserId(userID: String) {
        prefs.edit().putString(PREFS_USER_ID, userID).apply()
    }

    fun getIsUserPhoneVerified() : Boolean {
        return prefs.getBoolean(PREFS_IS_USER_PHONE_VERIFIED, false)
    }

    fun setIsUserPhoneVerified(isUserPhoneVerified: Boolean) {
        prefs.edit().putBoolean(PREFS_IS_USER_PHONE_VERIFIED, isUserPhoneVerified).apply()
    }
}