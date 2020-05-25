@file:Suppress("PropertyName")

package com.murrayde.animekingmobile.view.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.databinding.SearchItemBinding
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.view.search.SearchFragmentDirections

class SearchRecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>(), ClickListener {

    val list_anime = mutableListOf<AnimeData>()

    fun updateList(list: List<AnimeData>) {
        list_anime.clear()
        list_anime.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: SearchItemBinding = DataBindingUtil.inflate(inflater, R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list_anime.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item_view.animeId = list_anime[position]
        holder.item_view.listener = this
    }

    override fun onClick(view: View) {
        list_anime.forEach { current_anime ->
            if (view.tag == current_anime.id) {
                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(current_anime.attributes)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    inner class ViewHolder(view: SearchItemBinding) : RecyclerView.ViewHolder(view.root) {
        val item_view: SearchItemBinding = view
    }
}