package com.prasan.kotlinmvvmhiltflowapp.domain.usecase

import com.prasan.kotlinmvvmhiltflowapp.APICallResult
import com.prasan.kotlinmvvmhiltflowapp.UIState
import com.prasan.kotlinmvvmhiltflowapp.data.FHPRepository
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import com.prasan.kotlinmvvmhiltflowapp.domain.contract.IRepository
import com.prasan.kotlinmvvmhiltflowapp.domain.contract.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [UseCase] class implementation that retrieves a paginated list of photos from the service
 * Takes a page number as input and returns the [PhotoResponse] instance in return
 * @author Prasan
 * @since 1.0
 */
@Singleton
class GetPopularPhotosUseCase @Inject constructor(private val repository: IRepository) :
    UseCase<Int, PhotoResponse> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Int): Flow<UIState<PhotoResponse>> = flow {
        emit(UIState.LoadingState(true))
        (repository as FHPRepository).getPopularPhotos(input).collect {
            when (it) {
                is APICallResult.OnSuccessResponse -> emit(UIState.OnOperationSuccess(it.data))
                is APICallResult.OnErrorResponse -> emit(UIState.OnOperationFailed(it.exception))
            }
            emit(UIState.LoadingState(false))
        }
    }.flowOn(Dispatchers.IO)
}