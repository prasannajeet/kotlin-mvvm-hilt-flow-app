package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse

/**
 * [UseCase] class implementation that retrieves a paginated list of photos from the service
 * Takes a page number as input and returns the [PhotoResponse] instance in return
 * @author Prasan
 * @since 1.0
 */
class GetPopularPhotosUseCase : UseCase<Int, PhotoResponse> {
    override suspend fun execute(input: Int): APICallResult<PhotoResponse> =
        FHPRepository.getPopularPhotos(input)
}