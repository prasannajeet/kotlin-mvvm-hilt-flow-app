package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.BuildConfig
import com.prasan.a500pxcodingchallenge.baseURL
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse
import com.prasan.a500pxcodingchallenge.model.network.FiveHundredPixelsAPI
import com.prasan.a500pxcodingchallenge.safeApiCall
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The repository class represents the data store of the application. This class is primarily utilised
 * when building offline-first applications where it will make the determination to load the data from
 * a local Room DB vs calling the retrofit function in order to obtain the data
 * @author Prasan
 * @since 1.0
 */
object FHPRepository {

    private val retrofitClient: FiveHundredPixelsAPI by lazy {
        Retrofit.Builder().run {
            baseUrl(baseURL)
            addConverterFactory(MoshiConverterFactory.create())
            client(OkHttpClient().apply {
                OkHttpClient.Builder().run {
                    addInterceptor(HttpLoggingInterceptor())
                    build()
                }
            })
            build()
        }.run {
            create(FiveHundredPixelsAPI::class.java)
        }
    }

    /**
     * Performs the popular photos API call. In an offline-first architecture, it is at this function
     * call that the Repository class would check if the data for the given page number exists in a Room
     * table, if so return the data from the db, else perform a retrofit call to obtain and store the data
     * into the db before returning the same
     * @param pageNumber Page number of the data called in a paginated data source
     */
    suspend fun getPopularPhotos(pageNumber: Int): APICallResult<PhotoResponse> =
        safeApiCall("Error Obtaining Photos") {
            retrofitClient.getPopularPhotos(
                BuildConfig.API_KEY,
                "popular",
                pageNumber
            )
        }

}