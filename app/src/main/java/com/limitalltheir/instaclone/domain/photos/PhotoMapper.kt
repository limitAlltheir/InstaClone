package com.limitalltheir.instaclone.domain.photos

import com.limitalltheir.instaclone.data.dto.PhotoDto
import com.limitalltheir.instaclone.data.model.PhotoItem

object PhotoMapper {

    fun map(dto: PhotoDto.PhotoDtoItem) =
        PhotoItem(
            id = dto.id,
            user = PhotoItem.User(
                userName = dto.user?.username,
                avatarUrl = dto.user?.profileImage?.small ?: dto.user?.profileImage?.medium
                ?: dto.user?.profileImage?.large.orEmpty()
            ),
            likesCount = dto.likes ?: 0,
            photoUrl = dto.urls?.full ?: dto.urls?.raw ?: dto.urls?.regular ?: dto.urls?.small
            ?: dto.urls?.thumb.orEmpty(),
            description = dto.description
        )
}