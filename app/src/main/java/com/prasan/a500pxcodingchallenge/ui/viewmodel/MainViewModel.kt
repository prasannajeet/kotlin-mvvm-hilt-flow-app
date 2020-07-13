package com.prasan.a500pxcodingchallenge.ui.viewmodel

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.domain.GetPopularPhotosUseCase
import com.prasan.a500pxcodingchallenge.model.datamodel.Photo
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoDetails
import kotlinx.coroutines.launch

/**
 * In an MVVM architecture, the [ViewModel] acts as the point at which the view and the data layers
 * of the applicable interface in order to implemented the business logic. This ViewModel contains
 * methods to show the list of popular photos in a paginated fashion as well as to parse the [Bundle]
 * arguments to show details of the page. In this app architecture, a single ViewModel is shared between
 * all fragments in the application
 * @author Prasan
 * @since 1.0
 */
class MainViewModel : ViewModel() {

    /**
     * [MutableLiveData] to notify the Popular photos list view with the list of photos
     */
    val popularPhotosLiveData: MutableLiveData<UIState<List<Photo>>> by lazy {
        MutableLiveData<UIState<List<Photo>>>()
    }

    /**
     * [MutableLiveData] to provide the [PhotoDetails] parsed value for a particular photo
     */
    val photoDetailsLiveData: MutableLiveData<UIState<PhotoDetails>> by lazy {
        MutableLiveData<UIState<PhotoDetails>>()
    }

    private var currentPageNumber = 1 // Page number currently displayed in UI
    private var maximumPageNumber = 2 // Total number of pages available in the paginated service
    private val photoList =
        ArrayList<Photo>() // VM maintains the list of photos and adds new photos per page

    var navigatingFromDetails =
        false // This is a dirty implementation to avoid reloading the popular photo
    // fragment when back navigating from details. This is an issue with the Android Navigation Component that destroys
    // the fragment after navigation. Alternative implementation to store view in a variable will have memory leak
    // implications (https://twitter.com/ianhlake/status/1103522856535638016)

    /**
     * Retrieve the photos per page from the popular photos API and inform the view by [MutableLiveData]
     * @since 1.0
     */
    fun getPhotosNextPage() {

        if (navigatingFromDetails) {
            popularPhotosLiveData.value = UIState.OnOperationSuccess(photoList)
            return
        }


        if (currentPageNumber < maximumPageNumber) {

            viewModelScope.launch {

                popularPhotosLiveData.value = UIState.LoadingState(true)
                when (val result = GetPopularPhotosUseCase().execute(currentPageNumber)) {
                    is APICallResult.OnErrorResponse ->
                        popularPhotosLiveData.value = UIState.OnOperationFailed(result.exception)
                    is APICallResult.OnSuccessResponse -> {
                        currentPageNumber++
                        maximumPageNumber = result.data.totalPages
                        photoList.addAll(result.data.photos)
                        popularPhotosLiveData.value =
                            UIState.OnOperationSuccess(photoList)
                    }
                }
                popularPhotosLiveData.value = UIState.LoadingState(false)
            }
        }
    }

    /**
     * Process the [Bundle] argument from the list fragment to process the photo details
     * @param args [Bundle] object containing parcelized [PhotoDetails] instance
     * @since 1.0
     */
    fun processPhotoDetailsArgument(@NonNull args: Bundle) {

        val photoDetails = args.getParcelable<PhotoDetails>("photoDetails")

        photoDetails?.let {
            photoDetailsLiveData.value = UIState.OnOperationSuccess(it)
        } ?: run {
            photoDetailsLiveData.value =
                UIState.OnOperationFailed(Exception("No Photo Details found"))
        }
    }

    /**
     * ViewModel function called by view when the list is scrolled to its bottommost position
     * in order to load the next page of data from the serve
     */
    fun onRecyclerViewScrolledToBottom() {
        if (navigatingFromDetails) navigatingFromDetails = false
        getPhotosNextPage()
    }
}