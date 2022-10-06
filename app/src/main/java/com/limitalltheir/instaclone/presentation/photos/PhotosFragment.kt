package com.limitalltheir.instaclone.presentation.photos

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.limitalltheir.instaclone.R
import com.limitalltheir.instaclone.presentation.base.BaseFragment
import com.limitalltheir.instaclone.util.Constants
import com.limitalltheir.instaclone.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photos.*


@AndroidEntryPoint
class PhotosFragment : BaseFragment(R.layout.fragment_photos) {

    private val viewModel: PhotosViewModel by viewModels()

    private val photoAdapter = PhotosAdapter()

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isLastPage = viewModel.isLastPage

        rvPhotos?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = photoAdapter
            addOnScrollListener(this@PhotosFragment.scrollListener)
        }


        viewModel.photos.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Result.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        photoAdapter.setItems(it)
                    }
                    if (isLastPage) {
                        rvPhotos.setPadding(0, 0, 0, 0)
                    }
                }
                is Result.Loading -> {
                    showProgressBar(viewModel.page <= 1)
                }
                is Result.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
        progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(isFirst: Boolean) {
        if (isFirst) {
            progressBar.visibility = View.VISIBLE
            paginationProgressBar.visibility = View.INVISIBLE
        } else {
            paginationProgressBar.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisiblePosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisiblePosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getPhotos()
                isScrolling = false
            }
        }
    }
}