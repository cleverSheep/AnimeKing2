package com.murrayde.retrofittesting.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.AnimeData
import com.murrayde.retrofittesting.util.Util

class AnimeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.tv_anime_name)
    private val imageView = itemView.findViewById<ImageView>(R.id.iv_anime_image)

    var data: AnimeData? = null

    fun bindTo(data: AnimeData) {
        this.data = data
        nameView.text = data.attributes.canonicalTitle
        Util.loadImage(imageView, data.attributes.posterImage.original)
    }
}