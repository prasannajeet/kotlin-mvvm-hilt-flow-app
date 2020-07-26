package com.prasan.a500pxcodingchallenge.model.network

import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * Retrofit API class for the 500px API
 * @author Prasan
 * @since 1.0
 */
@Singleton
interface FiveHundredPixelsAPI {

    /**
     * Performs a GET call to obtain a paginated list of photos
     * @param key API Key
     * @param feature feature source the photos should come from
     * @param page Page number of the data where the photos should come from
     * @return [Response] instance of [PhotoResponse] type
     */
    @GET("/v1/photos?image_size=5,6")
    suspend fun getPopularPhotos(
        @Query("consumer_key") key: String,
        @Query("feature") feature: String,
        @Query("page") page: Int
    ): Response<PhotoResponse>

    //NOTE: As per https://github.com/500px/legacy-api-documentation/blob/master/basics/formats_and_terms.md#image-urls-and-image-sizes
    // image_size=5,6 should return a array of Images with variable sizes, but in the response only 1 size is being obtained

}