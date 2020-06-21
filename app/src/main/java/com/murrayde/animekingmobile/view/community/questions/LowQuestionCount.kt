package com.murrayde.animekingmobile.view.community.questions


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.view.community.LowQuestionCountDirections
import com.murrayde.animekingmobile.view.community.list_anime.AnimeListDetailArgs
import kotlinx.android.synthetic.main.fragment_low_question_count.*

class LowQuestionCount : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private lateinit var media: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_low_question_count, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_low_questions)
        toolbar.setupWithNavController(findNavController())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)

        button_transition_ask_question.setOnClickListener {
            media.start()
            val action = LowQuestionCountDirections.actionLowQuestionCountFragmentToAskQuestionFragment(attributes)
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }
}
