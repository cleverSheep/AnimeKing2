@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.R
import timber.log.Timber

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val header_title = view.findViewById<TextView>(R.id.header_title)
    private val header_sub_title = view.findViewById<TextView>(R.id.header_sub_title)

    init {
        view.setOnClickListener {
            Timber.d("Header clicked!!")
        }
    }

    fun bindTo(title: String, sub_title: String) {
        header_title.text = title
        header_sub_title.text = sub_title
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}