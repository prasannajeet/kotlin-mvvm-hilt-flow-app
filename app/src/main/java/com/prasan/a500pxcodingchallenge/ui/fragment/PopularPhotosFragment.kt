package com.prasan.a500pxcodingchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.databinding.PopularPhotosFragmentBinding
import com.prasan.a500pxcodingchallenge.getFormattedExifData
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoDetails
import com.prasan.a500pxcodingchallenge.showToast
import com.prasan.a500pxcodingchallenge.ui.PopularPhotosAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {

                is UIState.LoadingState ->
                    binding.loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                is UIState.OnOperationFailed ->
                    uiState.exception.message?.let { toastMessage ->
                        showToast(toastMessage)
                    }
                is UIState.OnOperationSuccess -> {
                    binding.popularPhotoList.adapter = PopularPhotosAdapter(uiState.output) {

                        val photoDetails = PhotoDetails(
                            it.images[0].httpsUrl,
                            it.description,
                            it.votesCount,
                            it.commentsCount,
                            it.rating,
                            it.timesViewed,
                            it.name,
                            it.user.fullname,
                            it.user.avatars.default.https,
                            it.getFormattedExifData()
                        )
                        findNavController()
                            .navigate(
                                PopularPhotosFragmentDirections
                                    .actionPopularPhotosFragmentToPhotoDetailsFragment(photoDetails)
                            )
                    }
                }
            }
        })
        viewModel.getPopularPhotos()
    }
}