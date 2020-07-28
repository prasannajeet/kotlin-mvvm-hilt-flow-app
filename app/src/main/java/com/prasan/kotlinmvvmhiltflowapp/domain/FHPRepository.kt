package com.prasan.kotlinmvvmhiltflowapp.domain

import com.prasan.kotlinmvvmhiltflowapp.BuildConfig
import com.prasan.kotlinmvvmhiltflowapp.model.network.FiveHundredPixelsAPI
import com.prasan.kotlinmvvmhiltflowapp.performSafeNetworkApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The repository class represents the data store of the application. This class is primarily utilised
 * when building offline-first applications where it will make the determination to load the data from
 * a local Room DB vs calling the retrofit function in order to obtain the data
 * @author Prasan
 * @since 1.0
 */
@Singleton
class FHPRepository @Inject constructor(private val retrofitClient: FiveHundredPixelsAPI) :
    IRepository {

    /**
     * Performs the popular photos API call. In an offline-first architecture, it is at this function
     * call that the Repository class would check if the data for the given page number exists in a Room
     * table, if so return the data from the db, else perform a retrofit call to obtain and store the data
     * into the db before returning the same
     * @param pageNumber Page number of the data called in a paginated data source
     */
    @ExperimentalCoroutinesApi
    suspend fun getPopularPhotos(pageNumber: Int) =
        performSafeNetworkApiCall("Error Obtaining Photos") {
            retrofitClient.getPopularPhotos(
                BuildConfig.API_KEY,
                "popular",
                pageNumber
            )
        }
}