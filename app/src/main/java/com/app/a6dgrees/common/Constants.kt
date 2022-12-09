package com.app.a6dgrees.common

object Constants {
    const val BASE_DEV_URL = "https://test.stytch.com/v1/"
    const val USERNAME = "project-test-8b6f9f80-0c3c-4120-b0bc-7a67a28c1945"
    const val PASSWORD = "secret-test-pVXGw0HHCFiwJeAXykwVQPCQd8wNFcjTkUU="

    //Shared Prefs
    const val SHARED_PREFS_NAME = "six-degrees-prefs"
    const val PREFS_USER_MODEL = "user_id"
    const val PREFS_IS_USER_PHONE_VERIFIED = "isUserPhoneVerified"
    const val PREFS_USER_ID = "userID"
    const val HAS_SEEN_ONBOARDING = "has_seen_onboarding"
    const val IS_EMAIL_VERIFIED = "is_email_verified"

    //regex
    val firstNameRegex = Regex("""^[\p{L}\p{M}\p{S}\p{N}\p{P}]{2,}([ \-\']?[\p{L}\p{M}\p{S}\p{N}\p{P}]+)*$""")
    val lastNameRegex = Regex("""^[\p{L}\p{M}\p{S}\p{N}\p{P}]{2,}([ \-\']?[\p{L}\p{M}\p{S}\p{N}\p{P}]+)*$""")
    val phoneNumberRegex = Regex("""^(\+254|0)[7-9]\d{8}${'$'}""")
    val passwordRegex = Regex("""^(?=.*[\d])(?=.*[\W]).{8,}$""")
   // val cleanedPhoneNumber = Regex("""^[\d\s()+-]+${'$'}""")
    val emailRegex = Regex("""^[^@\s]+@[^@\s]+\.[^@\s]+${'$'}""")

    val loginPasswordRegex = Regex("""^(?=.*[A-Za-z])(?=.*\d)(?=.*[@${'$'}!%*#?&])[A-Za-z\d@${'$'}!%*#?&]{8,}${'$'}""")


}