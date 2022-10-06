package com.limitalltheir.instaclone.domain.photos

import com.limitalltheir.instaclone.domain.retrofit.RetrofitInstance
import javax.inject.Inject

class PhotoRepository @Inject constructor() {
    suspend fun getPhotos(page: Int) =
        RetrofitInstance.api.getPhotos(page)
}