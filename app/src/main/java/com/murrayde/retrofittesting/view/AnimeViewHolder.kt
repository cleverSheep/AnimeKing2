@file:Suppress("LocalVariableName")

package com.murrayde.retrofittesting.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.AnimeData
import com.murrayde.retrofittesting.util.Util

class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    
    private val nameView = itemView.findViewById<TextView>(R.id.tv_anime_name)
    private val imageView = itemView.findViewById<ImageView>(R.id.iv_anime_image)

    init {
        view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(data!!.attributes)
            Navigation.findNavController(it).navigate(action)
        }
    }

    var data: AnimeData? = null

    fun bindTo(data: AnimeData) {
        this.data = data
        nameView.text = data.attributes.titles.en ?: data.attributes.canonicalTitle
        Util.loadImage(imageView, data.attributes.posterImage.original)
    }

    companion object {
        fun create(parent: ViewGroup): AnimeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime, parent, false)
            return AnimeViewHolder(view)
        }
    }
}