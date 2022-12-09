package com.app.a6dgrees.common

import android.view.View
import androidx.navigation.NavController
import com.app.a6dgrees.domain.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

fun showSnackBar(msg: String, view: View, length: Int) {
    Snackbar.make(view, msg, length).show()
}

fun fromJson(string: String): UserModel {
    return Gson().fromJson(string, UserModel::class.java)
}

fun toJson(userModel: UserModel): String {
    return Gson().toJson(userModel)
}

fun navigateAndPopBackStack(
    navController: NavController,
    action: Int,
    fragmentLogin: Int
) {
    navController.apply {
        this.navigate(action)
        this.popBackStack(fragmentLogin, true)
    }
}