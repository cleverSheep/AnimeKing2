@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.screen.quiz_results


import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.murrayde.animekingmobile.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewResultsList : Fragment() {

    private val resultsArgs: ViewResultsListArgs by navArgs()
    private lateinit var linearLayoutBottomSheet: LinearLayout
    private lateinit var resultsViewModel: ResultsViewModel
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_results, container, false)
        resultsViewModel = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
    }
}
