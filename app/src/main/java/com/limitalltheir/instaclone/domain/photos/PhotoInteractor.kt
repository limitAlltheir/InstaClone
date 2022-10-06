package com.limitalltheir.instaclone.domain.photos

import com.limitalltheir.instaclone.data.model.PhotoItem
import com.limitalltheir.instaclone.util.Result
import javax.inject.Inject

class PhotoInteractor @Inject constructor(
    private val repo: PhotoRepository
) {

    var totalPosts: Int = 20

    suspend fun getPhotos(page: Int): Result<List<PhotoItem>> {
        return getApiPhotos(page)
    }

    private suspend fun getApiPhotos(page: Int): Result<List<PhotoItem>> {

        val response = repo.getPhotos(page)

        totalPosts = response.headers().firstOrNull { it.first == "x-total" }?.second?.toInt() ?: 20

        return if (response.isSuccessful) {

            val data = response.body()?.let { dto ->
                dto.map {
                    PhotoMapper.map(it)
                }
            }
            Result.Success(data)
        } else {
            Result.Error(response.message())
        }
    }
}