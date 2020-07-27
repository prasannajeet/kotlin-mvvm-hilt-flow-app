package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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
    override suspend fun execute(input: Int): Flow<APICallResult<PhotoResponse>> =
        (repository as FHPRepository).getPopularPhotos(input)
}