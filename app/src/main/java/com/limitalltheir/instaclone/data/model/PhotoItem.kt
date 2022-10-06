package com.limitalltheir.instaclone.data.model

data class PhotoItem(
    val id: String? = null,
    val user: User? = null,
    val likesCount: Int = 0,
    val photoUrl: String = DEFAULT_IMAGE,
    val description: String? = null
) {
    data class User(
        val userName: String? = null,
        val avatarUrl: String = DEFAULT_AVATAR
    )

    companion object {
        private const val DEFAULT_IMAGE = "https://www.stalker-co.ru/images/no_image.png"
        private const val DEFAULT_AVATAR = "https://img2.freepng.ru/20180521/ocp/kisspng-computer-icons-user-profile-avatar-french-people-5b0365e4f1ce65.9760504415269493489905.jpg"
    }
}