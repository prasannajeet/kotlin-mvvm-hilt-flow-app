package com.prasan.kotlinmvvmhiltflowapp.data.contract

import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response

/**
 * This interface provides contracts a web-service class needs to abide by to provide the app
 * with network data as required
 * @author Prasan
 * @since 1.0
 */
interface IWebService {

    /**
     * Performs the popular photos API call. In an offline-first architecture, it is at this function
     * call that the Repository class would check if the data for the given page number exists in a Room
     * table, if so return the data from the db, else perform a retrofit call to obtain and store the data
     * into the db before returning the same
     * @param apiKey The api key needed to make the service call
     * @param pageNumber Page number of the data called in a paginated data source
     * @param feature Which feature the photos will be requested from
     * @return [Response] of [PhotoResponse] type
     */
    @ExperimentalCoroutinesApi
    suspend fun getPhotosByPage(
        apiKey: String,
        feature: String,
        pageNumber: Int
    ): Response<PhotoResponse>
}