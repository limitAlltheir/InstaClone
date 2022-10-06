package com.limitalltheir.instaclone.util

import android.view.View
import android.widget.TextView

object Utils {

    fun View.setTextOrHide(text: String?) {
        if (text == null) {
            this.visibility = View.GONE
        } else {
            this.visibility = View.VISIBLE
            (this as TextView).text = text
        }
    }
}