@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.view.community.list_anime.item.AnimeViewHolder
import com.murrayde.animeking.view.community.list_anime.item.HeaderViewHolder
import timber.log.Timber

class AnimeListAdapter(val context: Context) : PagedListAdapter<AnimeData, RecyclerView.ViewHolder>(diffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val HEADER_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) AnimeViewHolder.create(parent) else HeaderViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            /** IndexOutOfBounds exception occurs when using item position*/
            (holder as AnimeViewHolder).bindTo(getItem(position - 1))
        } else {
            (holder as HeaderViewHolder).bindTo(context.resources.getString(R.string.random_anime))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE else DATA_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        Timber.d("Count calculated")
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