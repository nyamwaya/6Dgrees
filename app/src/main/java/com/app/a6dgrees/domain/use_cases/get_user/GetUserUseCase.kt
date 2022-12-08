package com.app.a6dgrees.domain.use_cases.get_user

import com.app.a6dgrees.common.Resource
import com.app.a6dgrees.data.remote.dto.GetUserResponseDto
import com.app.a6dgrees.domain.SixDegreesRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: SixDegreesRepository
) {
    operator fun invoke(userId: String): kotlinx.coroutines.flow.Flow<Resource<GetUserResponseDto>> =
        flow {
            try {
                emit(Resource.Loading())
                val getUserResponse = repository.getUser(userId)
                emit(Resource.Success(getUserResponse))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server, check your internet connection."))
            }
        }
}