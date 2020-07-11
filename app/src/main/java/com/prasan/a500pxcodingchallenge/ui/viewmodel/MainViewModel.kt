package com.prasan.a500pxcodingchallenge.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prasan.a500pxcodingchallenge.APICallResult
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.domain.GetPopularPhotosUseCase
import com.prasan.a500pxcodingchallenge.model.datamodel.Photo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val popularPhotosLiveData: MutableLiveData<UIState<List<Photo>>> = MutableLiveData()

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
}