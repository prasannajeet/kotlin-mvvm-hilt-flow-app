package com.prasan.kotlinmvvmhiltflowapp.domain.usecase

import com.prasan.kotlinmvvmhiltflowapp.NetworkOperationResult
import com.prasan.kotlinmvvmhiltflowapp.ViewState
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
    override suspend fun execute(input: Int): Flow<ViewState<PhotoResponse>> = flow {
        emit(ViewState.Loading(true))

        repository.getPhotosByPage(input).collect {
            when (it) {
                is NetworkOperationResult.OnSuccess -> emit(ViewState.RenderSuccess(it.data))
                is NetworkOperationResult.OnFailed -> emit(ViewState.RenderFailure(it.throwable))
            }
            emit(ViewState.Loading(false))
        }
    }.flowOn(Dispatchers.IO)
}