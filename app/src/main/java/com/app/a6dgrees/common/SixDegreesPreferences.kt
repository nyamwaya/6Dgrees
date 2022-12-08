package com.app.a6dgrees.common

import android.content.Context
import android.content.SharedPreferences
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
}