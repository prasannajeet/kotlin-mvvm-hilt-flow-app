package com.prasan.kotlinmvvmhiltflowapp.domain

import com.prasan.kotlinmvvmhiltflowapp.NetworkOperationResult
import com.prasan.kotlinmvvmhiltflowapp.ViewState
import com.prasan.kotlinmvvmhiltflowapp.contract.IRepository
import com.prasan.kotlinmvvmhiltflowapp.contract.IUseCase
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IUseCase] class implementation that retrieves a paginated list of photos from the service
 * Takes a page number as input and returns the [PhotoResponse] instance in return
 * @author Prasan
 * @since 1.0
 */
@Singleton
class GetPopularPhotosUseCase @Inject constructor(override val repository: IRepository) :
    IUseCase<Int, PhotoResponse> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Int): Flow<ViewState<PhotoResponse>> = flow {
        emit(ViewState.Loading(true))
        repository.getPhotosByPage(input).map {
            when (it) {
                is NetworkOperationResult.OnSuccess -> ViewState.RenderSuccess(it.data)
                is NetworkOperationResult.OnFailed -> ViewState.RenderFailure(it.throwable)
            }
        }.collect {
            emit(it)
        }
        emit(ViewState.Loading(false))
    }.flowOn(Dispatchers.IO)

}