package com.prasan.kotlinmvvmhiltflowapp.data

import com.prasan.kotlinmvvmhiltflowapp.NetworkOperationResult
import com.prasan.kotlinmvvmhiltflowapp.contract.IRemoteDataSource
import com.prasan.kotlinmvvmhiltflowapp.contract.IRepository
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class impl from [IRepository]
 * @author Prasan
 * @since 1.0
 * @see IRepository
 * @see IRemoteDataSource
 */
@Singleton
class FHPRepository @Inject constructor(override val remoteDataSource: IRemoteDataSource) :
    IRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getPhotosByPage(pageNumber: Int): Flow<NetworkOperationResult<PhotoResponse>> =
        remoteDataSource.getPhotosByPage(pageNumber)
}