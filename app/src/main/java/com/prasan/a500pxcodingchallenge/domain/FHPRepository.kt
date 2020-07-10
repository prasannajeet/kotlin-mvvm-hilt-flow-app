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

    suspend fun getPopularPhotos(): APICallResult<PhotoResponse> =
        safeApiCall("Error Obtaining Photos") {
            retrofitClient.getPopularPhotos(
                BuildConfig.API_KEY,
                "popular"
            )
        }

}