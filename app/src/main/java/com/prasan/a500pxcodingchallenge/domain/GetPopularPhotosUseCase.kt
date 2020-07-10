package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse

class GetPopularPhotosUseCase : UseCase<PhotoResponse> {
    override suspend fun execute(): APICallResult<PhotoResponse> = FHPRepository.getPopularPhotos()
}