@file:Suppress("PropertyName")

package com.murrayde.animekingmobile.view.auth

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.util.ImageUtil

class LoginFragmentRecyclerviewAdapter(private val anime_data_list: ArrayList<AnimeData>) : RecyclerView.Adapter<LoginFragmentRecyclerviewAdapter.ViewHolder>() {

    var isReady = anime_data_list.size > 0

    fun updateLoginList(animeDataList: ArrayList<AnimeData>) {
        anime_data_list.clear()
        anime_data_list.addAll(animeDataList)
        isReady = true
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.login_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return anime_data_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ImageUtil.loadImageLogin(holder.image_item, anime_data_list[position].attributes.posterImage.original)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_item: ImageView = itemView.findViewById(R.id.image_item)
    }

    interface ListReady {
        fun onReady()
    }
}