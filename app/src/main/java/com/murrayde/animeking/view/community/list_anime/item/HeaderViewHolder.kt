@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.R
import com.murrayde.animeking.view.community.list_anime.AnimeListDirections
import com.murrayde.animeking.view.landing_screen.LandingScreenDirections
import timber.log.Timber

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val header_title = view.findViewById<TextView>(R.id.header_title)
    private val header_sub_title = view.findViewById<TextView>(R.id.header_sub_title)
    private val header_play_button = view.findViewById<Button>(R.id.header_play_button)

    init {
        header_play_button.setOnClickListener { v ->
            val directions = AnimeListDirections.actionHomeToAnswerRandomQuestions()
            Navigation.findNavController(v).navigate(directions)
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