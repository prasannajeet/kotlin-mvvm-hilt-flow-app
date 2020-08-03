package com.prasan.kotlinmvvmhiltflowapp.data

import com.prasan.kotlinmvvmhiltflowapp.NetworkOperationResult
import com.prasan.kotlinmvvmhiltflowapp.contract.IRemoteDataSource
import com.prasan.kotlinmvvmhiltflowapp.contract.IWebService
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [IRemoteDataSource] impl class that provides access to network API calls
 * @author Prasan
 * @since 1.0
 * @see IRemoteDataSource
 * @see IWebService
 */
@Singleton
class NetworkDataSource @Inject constructor(override val webService: IWebService) :
    IRemoteDataSource {

    @ExperimentalCoroutinesApi
    override suspend fun getPhotosByPage(pageNumber: Int): Flow<NetworkOperationResult<PhotoResponse>> =
        webService.getPhotosByPage(pageNumber)
}