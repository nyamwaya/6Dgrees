package com.app.a6dgrees.domain.use_cases.login

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.LoginRequest
import com.app.a6dgrees.data.remote.dto.LoginResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(requestBody: LoginRequest): kotlinx.coroutines.flow.Flow<Resource<LoginResponseDto>> =
        flow {
            try {
                emit(Resource.Loading())
                val loginResponse = repository.login(requestBody)
                emit(Resource.Success(loginResponse))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection."))
            }
        }
}