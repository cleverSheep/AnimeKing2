@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime.item

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animeking.R
import com.murrayde.animeking.view.community.list_anime.AnimeListDirections

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val header_title = view.findViewById<TextView>(R.id.header_title)
    private val header_play_button = view.findViewById<Button>(R.id.header_play_button)
    private var sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(view.context)
    private var media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
    private var media = MediaPlayer.create(view.context, R.raw.button_click_sound_effect)


    init {
        header_play_button.setOnClickListener { v ->
            if (media_is_playing) media.start()
            val directions = AnimeListDirections.actionHomeToAnswerRandomQuestions()
            Navigation.findNavController(v).navigate(directions)
        }
    }

    fun bindTo(title: String) {
        header_title.text = title
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}