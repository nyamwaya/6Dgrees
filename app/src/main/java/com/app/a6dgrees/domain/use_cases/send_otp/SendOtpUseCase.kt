package com.app.a6dgrees.domain.use_cases.send_otp

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.PhoneOtp
import com.app.a6dgrees.data.remote.dto.RequestOtpResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendOtpUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(requestBody: PhoneOtp): Flow<Resource<RequestOtpResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val sendOtp = repository.sendOtp(requestBody)
            emit(Resource.Success(sendOtp))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection."))
        }
    }
}