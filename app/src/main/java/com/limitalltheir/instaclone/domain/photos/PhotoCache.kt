package com.limitalltheir.instaclone.domain.photos

import com.limitalltheir.instaclone.data.model.PhotoItem
import com.limitalltheir.instaclone.util.Result

class PhotoCache {

    private var photos: Result<List<PhotoItem>>? = null

    fun set(list: Result<List<PhotoItem>>) {
        photos = list
    }

    fun get() = photos
}