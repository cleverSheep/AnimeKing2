package com.murrayde.animeking.view.community.list_anime

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.view.community.list_anime.item.AnimeViewHolder
import com.murrayde.animeking.view.community.list_anime.item.HeaderViewHolder

class AnimeListAdapter : PagedListAdapter<AnimeData, RecyclerView.ViewHolder>(diffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val HEADER_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) AnimeViewHolder.create(parent) else HeaderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as AnimeViewHolder).bindTo(getItem(position))
        } else {
            (holder as HeaderViewHolder).bindTo("Random Anime Trivia", "How well rounded are you?")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE else DATA_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    companion object {

        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         */
        private val diffCallback = object : DiffUtil.ItemCallback<AnimeData>() {
            override fun areItemsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean = oldItem.id == newItem.id
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AnimeData, newItem: AnimeData): Boolean = oldItem == newItem

        }

    }
}