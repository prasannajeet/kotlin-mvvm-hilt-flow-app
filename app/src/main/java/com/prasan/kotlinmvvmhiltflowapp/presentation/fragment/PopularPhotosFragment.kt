package com.prasan.kotlinmvvmhiltflowapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.prasan.kotlinmvvmhiltflowapp.*
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.Photo
import com.prasan.kotlinmvvmhiltflowapp.data.datamodel.PhotoDetails
import com.prasan.kotlinmvvmhiltflowapp.databinding.PopularPhotosFragmentBinding
import com.prasan.kotlinmvvmhiltflowapp.presentation.MainViewModel
import com.prasan.kotlinmvvmhiltflowapp.presentation.PopularPhotosAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This [Fragment] displays a list of popular photos from 500px in a paginated fashion, the next page of data
 * is loaded when the scrolling of the current list of items is at its end. On loading, the API calls the first
 * page of data.
 * @author Prasan
 * @since 1.0
 * @see [MainViewModel]
 */
class PopularPhotosFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: PopularPhotosFragmentBinding
    private val photoItemClickListener: ListItemClickListener<Photo> = {
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
            it.getFormattedExifData(),
            it.durationPosted()
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


    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.popularPhotoList.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)

        binding.popularPhotoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.onRecyclerViewScrolledToBottom()
                }
            }
        })

        viewModel.popularPhotosLiveData.observe(viewLifecycleOwner, Observer { viewState ->
            when (viewState) {

                is ViewState.Loading ->
                    binding.loading.visibility =
                        if (viewState.isLoading) View.VISIBLE else View.GONE

                is ViewState.RenderFailure ->
                    viewState.throwable.message?.let { toastMessage ->
                        context?.showToast(toastMessage)
                    }

                is ViewState.RenderSuccess -> {

                    if (binding.popularPhotoList.adapter == null) {
                        binding.popularPhotoList.adapter = PopularPhotosAdapter(
                            photoItemClickListener
                        )
                    }

                    (binding.popularPhotoList.adapter as PopularPhotosAdapter).itemList =
                        viewState.output
                }
            }
        })
        viewModel.getPhotosNextPage()
    }
}