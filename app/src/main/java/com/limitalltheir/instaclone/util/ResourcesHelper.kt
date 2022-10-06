package com.limitalltheir.instaclone.util

import android.content.Context
import androidx.annotation.StringRes

class ResourcesHelper(private val context: Context) {

    fun getContext() = context

    fun getString(@StringRes resId: Int) = context.getString(resId)
}