package com.limitalltheir.instaclone.domain.retrofit

import com.limitalltheir.instaclone.data.dto.PhotoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page")
        page: Int = 1,
        @Query("per_page")
        perPage: Int = 20
    ): Response<PhotoDto>
}