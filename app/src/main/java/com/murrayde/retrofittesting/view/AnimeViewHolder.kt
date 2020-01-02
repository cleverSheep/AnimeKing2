package com.murrayde.retrofittesting.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.AnimeAttributes.Attributes
import com.murrayde.retrofittesting.model.AnimeData
import com.murrayde.retrofittesting.util.Util

class AnimeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)) {

    private val name_view = itemView.findViewById<TextView>(R.id.tv_anime_name)
    private val image_view = itemView.findViewById<ImageView>(R.id.iv_anime_image)

    var data: AnimeData? = null

    fun bindTo(data: AnimeData) {
        this.data = data
        name_view.text = data.attributes.canonicalTitle
        Util.loadImage(image_view, data.attributes.posterImage.original)
    }
}