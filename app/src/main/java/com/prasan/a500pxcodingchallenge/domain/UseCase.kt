package com.prasan.a500pxcodingchallenge.domain

import com.prasan.a500pxcodingchallenge.APICallResult

/**
 * A UseCase defines a specific task performed in the app. For this project there would be two:
 * 1. Get Popular Photos
 * 2. Get details of a photo
 * [T] type defines the output of the use-case execution
 * @author Prasan
 */
interface UseCase<out T : Any> {

    /**
     * Execution contract which will run the business logic associated with completing a
     * particular use case
     * @since 1.0
     * @return [T] model type used to define the UseCase class
     */
    suspend fun execute(): APICallResult<T>
}