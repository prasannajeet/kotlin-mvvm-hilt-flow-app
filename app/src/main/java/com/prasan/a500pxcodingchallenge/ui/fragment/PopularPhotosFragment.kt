package com.prasan.a500pxcodingchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.databinding.PopularPhotosFragmentBinding
import com.prasan.a500pxcodingchallenge.showToast
import com.prasan.a500pxcodingchallenge.ui.viewmodel.MainViewModel

class PopularPhotosFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    private lateinit var binding: PopularPhotosFragmentBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PopularPhotosFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { uiState ->

            when (uiState) {
                is UIState.LoadingState ->
                    binding.loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                is UIState.OnOperationFailed -> showToast(uiState.exception.message!!)
                is UIState.OnOperationSuccess -> {
                    binding.popularPhotoList.adapter = uiState.output
                }
            }
        })

        viewModel.getPopularPhotos()
    }
}