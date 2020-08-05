package com.prasan.kotlinmvvmhiltflowapp.domain

import com.prasan.kotlinmvvmhiltflowapp.IOTaskResult
import com.prasan.kotlinmvvmhiltflowapp.contract.IRepository
import com.prasan.kotlinmvvmhiltflowapp.contract.IUseCase
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IUseCase] class implementation that retrieves a paginated list of photos from the service
 * Takes a page number as input and returns the [IOTaskResult] [PhotoResponse] instance in return
 * @author Prasan
 * @since 1.0
 */
@Singleton
class GetPopularPhotosUseCase @Inject constructor(override val repository: IRepository) :
    IUseCase<Int, PhotoResponse> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Int): Flow<IOTaskResult<PhotoResponse>> =
        repository.getPhotosByPage(input)
}