package com.prasan.a500pxcodingchallenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.TestCoroutineRule
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.domain.FHPRepository
import com.prasan.a500pxcodingchallenge.model.datamodel.Photo
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoResponse
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    private val mockRepository: FHPRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @RelaxedMockK
    private lateinit var uiStateObserver: Observer<UIState<List<Photo>>>

    @RelaxedMockK
    private lateinit var mockPhotoResponse: PhotoResponse

    @RelaxedMockK
    private lateinit var mockException: Exception


    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `load first page of Photos when getPopular photos is called for the first time`() {

        runBlockingTest {

            mockkObject(FHPRepository)
            coEvery { FHPRepository.getPopularPhotos(1) } returns APICallResult.OnSuccessResponse(
                mockPhotoResponse
            )


            viewModel.popularPhotosLiveData.observeForever(uiStateObserver)
            viewModel.getPhotosNextPage()

            verifyOrder {
                uiStateObserver.onChanged(UIState.LoadingState(true))
                uiStateObserver.onChanged(UIState.OnOperationSuccess(anyList()))
                uiStateObserver.onChanged(UIState.LoadingState(false))
            }
        }
    }

    @Test
    fun `when load photos service throws error UIState is OnOperationError`() {

        runBlockingTest {

            mockkObject(FHPRepository)

            every { mockException.message } returns "Test Exception"

            coEvery { FHPRepository.getPopularPhotos(1) } returns APICallResult.OnErrorResponse(
                mockException
            )


            viewModel.popularPhotosLiveData.observeForever(uiStateObserver)
            viewModel.getPhotosNextPage()

            verifyOrder {
                uiStateObserver.onChanged(UIState.LoadingState(true))
                uiStateObserver.onChanged(UIState.OnOperationFailed(mockException))
                uiStateObserver.onChanged(UIState.LoadingState(false))
            }
        }
    }

    @Test
    fun `when list scrolled to the bottom then next page of photos is called`() {

        runBlockingTest {

            mockkObject(FHPRepository)
            coEvery { FHPRepository.getPopularPhotos(1) } returns APICallResult.OnSuccessResponse(
                mockPhotoResponse
            )

            viewModel.popularPhotosLiveData.observeForever(uiStateObserver)
            viewModel.onRecyclerViewScrolledToBottom()

            verifyOrder {
                uiStateObserver.onChanged(UIState.LoadingState(true))
                uiStateObserver.onChanged(UIState.OnOperationSuccess(anyList()))
                uiStateObserver.onChanged(UIState.LoadingState(false))
            }
        }
    }

    @Test
    fun `when list scrolled to the bottom and error in service call error propagated in livedata`() {

        runBlockingTest {

            mockkObject(FHPRepository)

            every { mockException.message } returns "Test Exception"

            coEvery { FHPRepository.getPopularPhotos(1) } returns APICallResult.OnErrorResponse(
                mockException
            )


            viewModel.popularPhotosLiveData.observeForever(uiStateObserver)
            viewModel.onRecyclerViewScrolledToBottom()

            verifyOrder {
                uiStateObserver.onChanged(UIState.LoadingState(true))
                uiStateObserver.onChanged(UIState.OnOperationFailed(mockException))
                uiStateObserver.onChanged(UIState.LoadingState(false))
            }
        }
    }
}