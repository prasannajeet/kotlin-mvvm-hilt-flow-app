package com.prasan.kotlinmvvmhiltflowapp.data.network

import com.prasan.kotlinmvvmhiltflowapp.data.contract.IWebService
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IWebService] impl class which uses Retrofit to provide the app with the functionality to make
 * network requests
 * @author Prasan
 * @since 1.0
 * @see FiveHundredPixelsAPI
 * @see [IWebService]
 */
@Singleton
class RetrofitWebService @Inject constructor(private val retrofitClient: FiveHundredPixelsAPI) :
    IWebService {

    @ExperimentalCoroutinesApi
    override suspend fun getPhotosByPage(
        apiKey: String,
        feature: String,
        pageNumber: Int
    ): Response<PhotoResponse> =
        retrofitClient.getPopularPhotos(apiKey, feature, pageNumber)
}