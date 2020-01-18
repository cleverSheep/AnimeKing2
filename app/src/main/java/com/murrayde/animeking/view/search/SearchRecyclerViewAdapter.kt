package com.murrayde.animeking.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import timber.log.Timber

class SearchRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    val list_anime = mutableListOf<AnimeData>()

    fun updateList(list: List<AnimeData>) {
        Timber.d("List now updated!")
        list_anime.clear()
        list_anime.addAll(list)
        notifyDataSetChanged()
        Timber.d("Updated list size: %s", list_anime.size)
        repeat(list_anime.size) {
            Timber.d(list_anime[it].attributes.titles.en
                    ?: list_anime[it].attributes.canonicalTitle)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list_anime.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
                .load(list_anime[position].attributes.posterImage.large)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.search_anime_image)
        holder.search_anime_name.text = list_anime[position].attributes.titles.en
                ?: list_anime[position].attributes.canonicalTitle
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var search_anime_image = itemView.findViewById<ImageView>(R.id.search_anime_image)
        var search_anime_name = itemView.findViewById<TextView>(R.id.search_anime_name)
    }
}