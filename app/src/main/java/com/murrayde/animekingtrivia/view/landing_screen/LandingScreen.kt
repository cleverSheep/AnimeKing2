package com.murrayde.animekingtrivia.view.landing_screen


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.murrayde.animekingtrivia.R
import timber.log.Timber

class LandingScreen : Fragment() {

    private lateinit var media: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_landing_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // NOTE: This fragment can't be used as a context, instead use the activity
        // the fragment is attached to as a context
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)

    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
        Timber.d("Media release, fragment destroyed")
    }

}
