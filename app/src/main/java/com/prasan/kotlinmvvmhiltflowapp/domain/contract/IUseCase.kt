package com.prasan.kotlinmvvmhiltflowapp.domain.contract

import com.prasan.kotlinmvvmhiltflowapp.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

/**
 * A UseCase defines a specific task performed in the app. For this project there would be two:
 * 1. Get Popular Photos
 * 2. Get details of a photo
 * [O] type defines the output of the use-case execution
 * @author Prasan
 */
interface IUseCase<in I : Any, out O : Any> {

    val repository: IRepository

    /**
     * Execution contract which will run the business logic associated with completing a
     * particular use case
     * @param input [I] type input parameter
     * @since 1.0
     * @return [O] model type used to define the UseCase class
     */
    @ExperimentalCoroutinesApi
    suspend fun execute(input: I): Flow<ViewState<O>>
}