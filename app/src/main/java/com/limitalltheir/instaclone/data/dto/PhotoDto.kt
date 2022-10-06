package com.limitalltheir.instaclone.data.dto

import com.google.gson.annotations.SerializedName

class PhotoDto : ArrayList<PhotoDto.PhotoDtoItem>() {

    data class PhotoDtoItem(
        @SerializedName("description")
        val description: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("likes")
        val likes: Int?,
        @SerializedName("urls")
        val urls: Urls?,
        @SerializedName("user")
        val user: User?,
    ) {

        data class Urls(
            @SerializedName("full")
            val full: String?,
            @SerializedName("raw")
            val raw: String?,
            @SerializedName("regular")
            val regular: String?,
            @SerializedName("small")
            val small: String?,
            @SerializedName("thumb")
            val thumb: String?
        )

        data class User(
            @SerializedName("profile_image")
            val profileImage: ProfileImage?,
            @SerializedName("username")
            val username: String?
        ) {

            data class ProfileImage(
                @SerializedName("small")
                val small: String?,
                @SerializedName("medium")
                val medium: String?,
                @SerializedName("large")
                val large: String?,
            )
        }
    }
}