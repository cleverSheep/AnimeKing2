@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_anime.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.util.ImageUtil
import com.murrayde.animekingmobile.view.community.list_anime.AnimeListDirections

class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameView = itemView.findViewById<TextView>(R.id.tv_anime_name)
    private val imageView = itemView.findViewById<ImageView>(R.id.iv_anime_image)

    init {
        view.setOnClickListener { v ->
            val action = AnimeListDirections.actionListFragmentToDetailFragment(data!!.attributes)
            Navigation.findNavController(v).navigate(action)
        }
        view.setOnLongClickListener {
            Toast.makeText(view.context, "${data?.attributes?.titles?.en ?: data?.attributes?.canonicalTitle}", Toast.LENGTH_SHORT).show()
            true
        }
    }

    var data: AnimeData? = null

    fun bindTo(data: AnimeData?) {
        this.data = data
        nameView.text = data?.attributes?.titles?.en ?: data?.attributes?.canonicalTitle
        ImageUtil.loadImage(imageView, data?.attributes?.posterImage?.original)
    }

    companion object {
        fun create(parent: ViewGroup): AnimeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_anime, parent, false)
            return AnimeViewHolder(view)
        }
    }
}