package com.limitalltheir.instaclone.presentation.photos

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.limitalltheir.instaclone.R
import com.limitalltheir.instaclone.data.model.PhotoItem
import com.limitalltheir.instaclone.util.Utils.setTextOrHide
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private var items = ArrayList<PhotoItem>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: PhotoItem) {
            itemView.usernameTV.text = item.user?.userName
            itemView.likesTV.text = item.likesCount.toString()
            itemView.describtionTV.setTextOrHide(item.description)
            Glide.with(itemView).load(item.user?.avatarUrl).circleCrop().into(itemView.avatarIV)
            setMainImage(item)
        }

        private fun setMainImage(
            item: PhotoItem
        ) {
            itemView.progressBar.visibility = View.VISIBLE
            Glide.with(itemView).load(item.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.progressBar.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.progressBar.visibility = View.INVISIBLE
                        return false
                    }

                })
                .into(itemView.photoIV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun setItems(list: List<PhotoItem>) {
        DiffUtil.calculateDiff(Diff(items, list), true).dispatchUpdatesTo(this)
        items.clear()
        items.addAll(list)
    }

    private open inner class Diff(
        var oldItems: List<PhotoItem>,
        var newItems: List<PhotoItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].photoUrl == newItems[newItemPosition].photoUrl
        }
    }
}