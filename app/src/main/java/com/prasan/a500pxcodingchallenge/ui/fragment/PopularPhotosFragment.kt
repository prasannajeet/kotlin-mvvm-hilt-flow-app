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
import com.prasan.a500pxcodingchallenge.PhotoItemClickListener
import com.prasan.a500pxcodingchallenge.UIState
import com.prasan.a500pxcodingchallenge.databinding.PopularPhotosFragmentBinding
import com.prasan.a500pxcodingchallenge.getFormattedExifData
import com.prasan.a500pxcodingchallenge.model.datamodel.PhotoDetails
import com.prasan.a500pxcodingchallenge.showToast
import com.prasan.a500pxcodingchallenge.ui.PopularPhotosAdapter
import com.prasan.a500pxcodingchallenge.ui.viewmodel.MainViewModel
import com.prasan.a500pxcodingchallenge.ui.viewmodel.ViewModelFactory

/**
 * This [Fragment] displays a list of popular photos from 500px in a paginated fashion, the next page of data
 * is loaded when the scrolling of the current list of items is at its end. On loading, the API calls the first
 * page of data.
 * @author Prasan
 * @since 1.0
 * @see [MainViewModel]
 */
class PopularPhotosFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { ViewModelFactory() }
    private lateinit var binding: PopularPhotosFragmentBinding
    private val photoItemClickListener: PhotoItemClickListener = {
        val photoDetails = PhotoDetails(
            it.images[0].httpsUrl,
            it.description,
            it.votesCount,
            it.commentsCount,
            it.rating,
            it.timesViewed,
            it.name,
            it.user?.fullname,
            it.user?.avatars?.default?.https,
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
        retainInstance = true
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popularPhotoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.onRecyclerViewScrolledToBottom()
                }
            }
        })

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is UIState.LoadingState ->
                    binding.loading.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                is UIState.OnOperationFailed ->
                    uiState.throwable.message?.let { toastMessage ->
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
    }
}