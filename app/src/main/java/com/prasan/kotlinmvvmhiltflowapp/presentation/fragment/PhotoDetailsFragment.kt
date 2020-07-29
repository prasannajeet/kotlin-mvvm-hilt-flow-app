package com.prasan.kotlinmvvmhiltflowapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.prasan.kotlinmvvmhiltflowapp.ViewState
import com.prasan.kotlinmvvmhiltflowapp.databinding.FragmentPhotoDetailsBinding
import com.prasan.kotlinmvvmhiltflowapp.presentation.MainViewModel
import com.prasan.kotlinmvvmhiltflowapp.showToast

/**
 * [Fragment] displays details of the photo tapped on in [PopularPhotosFragment]
 * @author Prasan
 * @since 1.0
 * @see [MainViewModel]
 */
class PhotoDetailsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentPhotoDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(inflater)
        retainInstance = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            viewModel.processPhotoDetailsArgument(it)
                .observe(viewLifecycleOwner, Observer { uiState ->
                    when (uiState) {
                        is ViewState.RenderSuccess ->
                            binding.photoDetails = uiState.output
                        is ViewState.RenderFailure ->
                            context?.showToast(uiState.throwable.message!!)
                    }
                })
        } ?: context?.showToast("Invalid data")
    }
}