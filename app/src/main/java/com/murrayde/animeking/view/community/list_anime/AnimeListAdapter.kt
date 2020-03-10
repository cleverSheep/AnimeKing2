package com.murrayde.animeking.view.community.list_anime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.util.ImageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class AnimeListAdapter : PagedListAdapter<AnimeDataItem, RecyclerView.ViewHolder>(diffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> AnimeHeader.create(parent)
            ITEM_VIEW_TYPE_ITEM -> AnimeViewHolder.create(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AnimeViewHolder -> {
                val animeDataItem = getItem(position) as AnimeDataItem.AnimeItem
                holder.bindTo(animeDataItem.animeItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AnimeDataItem.AnimeListHeader -> ITEM_VIEW_TYPE_HEADER
            is AnimeDataItem.AnimeItem -> ITEM_VIEW_TYPE_ITEM
            null -> TODO()
        }
    }

    fun addHeaderAndSubmitList(list: PagedList<AnimeData>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(AnimeDataItem.AnimeListHeader)
                else -> listOf(AnimeDataItem.AnimeListHeader) + list.map { AnimeDataItem.AnimeItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class AnimeHeader(val view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun create(parent: ViewGroup): AnimeHeader {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_header, parent, false)
                return AnimeHeader(view)
            }
        }

    }

    class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameView = itemView.findViewById<TextView>(R.id.tv_anime_name)
        private val imageView = itemView.findViewById<ImageView>(R.id.iv_anime_image)

        init {
            view.setOnClickListener {
                val action = AnimeListDirections.actionListFragmentToDetailFragment(data!!.attributes)
                Navigation.findNavController(it).navigate(action)
            }
        }

        var data: AnimeData? = null

        /** Bind the anime data to the anime item layout*/
        fun bindTo(data: AnimeData) {
            this.data = data
            nameView.text = data.attributes.titles.en ?: data.attributes.canonicalTitle
            ImageUtil.loadImage(imageView, data.attributes.posterImage.original)
        }

        companion object {
            fun create(parent: ViewGroup): AnimeViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_anime, parent, false)
                return AnimeViewHolder(view)
            }
        }
    }

    companion object {

        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         */
        private val diffCallback = object : DiffUtil.ItemCallback<AnimeDataItem>() {
            override fun areItemsTheSame(oldItem: AnimeDataItem, newItem: AnimeDataItem): Boolean = oldItem.id == newItem.id
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AnimeDataItem, newItem: AnimeDataItem): Boolean = oldItem == newItem

        }

    }
}

sealed class AnimeDataItem {
    data class AnimeItem(val animeItem: AnimeData) : AnimeDataItem() {
        override val id: Long = animeItem.id.toLong()
    }

    object AnimeListHeader : AnimeDataItem() {
        override val id = Long.MIN_VALUE
    }

    abstract val id: Long
}

class AnimeDataListProvider {

}