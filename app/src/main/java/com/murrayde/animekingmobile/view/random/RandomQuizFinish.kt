package com.murrayde.animekingmobile.view.random

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager

import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.view.community.list_anime.AnimeListDetailDirections
import kotlinx.android.synthetic.main.finish_random_quiz.*

private lateinit var media: MediaPlayer
private var media_is_playing = true
private lateinit var sharedPreferences: SharedPreferences

class RandomQuizFinish : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.finish_random_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)

        fragment_random_finish_home.setOnClickListener {
            if (media_is_playing) media.start()
            val action = RandomQuizFinishDirections.actionRandomQuizFinishToHome()
            Navigation.findNavController(view).navigate(action)
        }
    }

}
