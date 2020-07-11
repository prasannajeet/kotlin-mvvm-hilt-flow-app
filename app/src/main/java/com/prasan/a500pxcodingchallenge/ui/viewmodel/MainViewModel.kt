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

class MainViewModel : ViewModel() {

    val popularPhotosLiveData: MutableLiveData<UIState<List<Photo>>> by lazy {
        MutableLiveData<UIState<List<Photo>>>()
    }
    val photoDetailsLiveData: MutableLiveData<UIState<PhotoDetails>> by lazy {
        MutableLiveData<UIState<PhotoDetails>>()
    }

    fun getPopularPhotos() {

        viewModelScope.launch {

            popularPhotosLiveData.value = UIState.LoadingState(true)

            when (val result = GetPopularPhotosUseCase().execute()) {
                is APICallResult.OnErrorResponse ->
                    popularPhotosLiveData.value = UIState.OnOperationFailed(result.exception)
                is APICallResult.OnSuccessResponse ->
                    popularPhotosLiveData.value =
                        UIState.OnOperationSuccess(result.data.photos)
            }

            popularPhotosLiveData.value = UIState.LoadingState(false)
        }
    }

    fun processPhotoDetailsArgument(@NonNull args: Bundle) {
        val photoDetails = args.getParcelable<PhotoDetails>("photoDetails")

        photoDetails?.let {
            photoDetailsLiveData.value = UIState.OnOperationSuccess(it)
        } ?: run {
            photoDetailsLiveData.value =
                UIState.OnOperationFailed(Exception("No Photo Details found"))
        }
    }
}