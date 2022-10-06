package com.limitalltheir.instaclone.presentation.photos

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.limitalltheir.instaclone.R
import com.limitalltheir.instaclone.data.model.PhotoItem
import com.limitalltheir.instaclone.domain.photos.PhotoInteractor
import com.limitalltheir.instaclone.presentation.base.BaseViewModel
import com.limitalltheir.instaclone.util.Constants
import com.limitalltheir.instaclone.util.Result
import com.limitalltheir.instaclone.util.ResourcesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val resourcesHelper: ResourcesHelper,
    private val photoInteractor: PhotoInteractor
) : BaseViewModel() {

    val photos: MutableLiveData<Result<List<PhotoItem>>> = MutableLiveData()
    var page = 1
    private val totalPages = photoInteractor.totalPosts / Constants.QUERY_PAGE_SIZE + 2
    var isLastPage = page == totalPages
    private var photosItem: MutableList<PhotoItem>? = null

    init {
        getPhotos()
    }

    fun getPhotos() = viewModelScope.launch {
        safePhotosCall()
    }

    private suspend fun safePhotosCall() {
        photos.postValue(Result.Loading())
        try {
            if (hasInternetConnection()) {
                val response = photoInteractor.getPhotos(page)

                page++
                photosItem = if (photosItem == null) {
                    response.data?.toMutableList()
                } else {
                    val oldPhotos = photosItem
                    val newPhotos = response.data
                    newPhotos?.let { oldPhotos?.addAll(it) }
                    oldPhotos?.toMutableList()
                }

                photos.postValue(Result.Success(photosItem))
            } else {
                photos.postValue(Result.Error(resourcesHelper.getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> photos.postValue(Result.Error(resourcesHelper.getString(R.string.server_error)))
                else -> photos.postValue(Result.Error(resourcesHelper.getString(R.string.error)))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            resourcesHelper.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}