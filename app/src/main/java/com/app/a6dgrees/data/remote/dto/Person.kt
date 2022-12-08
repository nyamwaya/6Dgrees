package com.app.a6dgrees.data.remote.dto

data class Person (
    val name: String,
    val phoneNumber: Int,
    val instagramUsername: String,
    val contacts: List<Contact>
)