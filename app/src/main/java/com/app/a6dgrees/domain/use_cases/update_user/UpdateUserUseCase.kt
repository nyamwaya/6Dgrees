package com.app.a6dgrees.domain.use_cases.update_user

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.UpdateUserRequest
import com.app.a6dgrees.data.remote.dto.UpdateUserResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(
        userId: String,
        requestBody: UpdateUserRequest
    ): Flow<Resource<UpdateUserResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val updateUserResponse = repository.updateUser(userId, requestBody)
            emit(Resource.Success(updateUserResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check your internet connection."))
        }
    }
}