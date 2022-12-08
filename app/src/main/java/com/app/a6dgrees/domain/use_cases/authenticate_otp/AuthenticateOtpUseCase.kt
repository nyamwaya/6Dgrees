package com.app.a6dgrees.domain.use_cases.authenticate_otp

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.AuthenticateOtpRequest
import com.app.a6dgrees.data.remote.dto.AuthenticateOtpResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthenticateOtpUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(requestBody: AuthenticateOtpRequest): Flow<Resource<AuthenticateOtpResponseDto>> =
        flow {
            try {
                emit(Resource.Loading())
                val authenticateOtp = repository.authenticateOtp(requestBody)
                emit(Resource.Success(authenticateOtp))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection."))
            }
        }
}