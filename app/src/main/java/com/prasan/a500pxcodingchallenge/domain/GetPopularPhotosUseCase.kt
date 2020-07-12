package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse

class GetPopularPhotosUseCase : UseCase<Int, PhotoResponse> {
    override suspend fun execute(input: Int): APICallResult<PhotoResponse> =
        FHPRepository.getPopularPhotos(input)
}