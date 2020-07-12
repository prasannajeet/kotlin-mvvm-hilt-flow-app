package com.prasan.a500pxcodingchallenge.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.prasan.a500pxcodingchallenge.*
import com.prasan.a500pxcodingchallenge.databinding.PopularPhotosFragmentBinding
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoDetails
import com.prasan.a500pxcodingchallenge.ui.PopularPhotosAdapter
import com.prasan.a500pxcodingchallenge.ui.viewmodel.MainViewModel

class PopularPhotosFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: PopularPhotosFragmentBinding
    private val paginationCallback: OnNextPageDataCallback = { viewModel.getPhotosNextPage() }
    private val photoItemClickListener: PhotoItemClickListener = {

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

        viewModel.navigatingFromDetails = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PopularPhotosFragmentBinding.inflate(inflater)

        binding.popularPhotoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    paginationCallback()
                }
            }
        })

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UIState.LoadingState ->
                    binding.loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                is UIState.OnOperationFailed ->
                    uiState.exception.message?.let { toastMessage ->
                        showToast(toastMessage)
                    }
                is UIState.OnOperationSuccess -> {

                    if (binding.popularPhotoList.adapter == null) {
                        binding.popularPhotoList.adapter = PopularPhotosAdapter(
                            photoItemClickListener
                        )
                    }

                    (binding.popularPhotoList.adapter as PopularPhotosAdapter).itemList =
                        uiState.output
                }
            }
        })

        viewModel.getPhotosNextPage()

        return binding.root
    }
}