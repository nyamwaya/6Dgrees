package com.app.a6dgrees.domain.use_cases.signup

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.LoginRequest
import com.app.a6dgrees.data.remote.dto.CreateUserWithPasswordResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(requestBody: LoginRequest): Flow<Resource<CreateUserWithPasswordResponseDto>> =
        flow {
            try {
                emit(Resource.Loading())
                val createUserWithPasswordResponse = repository.createUserWithPassword(requestBody)
                emit(Resource.Success(createUserWithPasswordResponse))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection."))
            }
        }
}