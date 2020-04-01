@file:Suppress("PropertyName", "PrivatePropertyName", "LocalVariableName")

package com.murrayde.animeking.view.community.list_anime

import android.content.SharedPreferences
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
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.murrayde.animeking.R
import com.murrayde.animeking.model.community.QuestionFactory
import com.murrayde.animeking.util.ImageUtil
import com.murrayde.animeking.view.MainActivity
import kotlinx.android.synthetic.main.fragment_detail.*
import java.util.prefs.AbstractPreferences


class AnimeListDetail : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private lateinit var questionFactory: QuestionFactory
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_detail)
        toolbar.setupWithNavController(findNavController())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)

        fragment_detail_title.text = attributes.titles.en ?: attributes.canonicalTitle
        fragment_detail_description.text = attributes.synopsis
        val cover_image = attributes.coverImage?.original ?: attributes.posterImage.original
        val poster_image = attributes.posterImage?.original ?: attributes.coverImage.original
        val age_rating = attributes.ageRating
        val release_date = attributes.createdAt.substring(0, 4)

        Glide.with(this)
                .load(cover_image)
                .placeholder(R.drawable.crown_list_screen)
                .dontAnimate()
                .into(fragment_detail_image)

        ImageUtil.loadDetailPosterImage(fragment_detail_poster_image, poster_image)
        fragment_detail_age_rating.text = age_rating
        fragment_detail_age_release_date.text = release_date

        questionFactory = QuestionFactory()
        fragment_detail_take_quiz.setOnClickListener {
            if (media_is_playing) media.start()
            questionFactory.hasEnoughQuestions(attributes.titles.en
                    ?: attributes.canonicalTitle, object : QuestionFactory.QuestionCountCallback {
                override fun onQuestionCountCallback(hasEnoughQuestions: Boolean) {
                    if (hasEnoughQuestions) {
                        startQuiz(it)
                    } else alertNotEnoughQuestions(it)
                }
            })
        }
        fragment_detail_ask_question.setOnClickListener {
            if (media_is_playing) media.start()
            val action = AnimeListDetailDirections.actionDetailFragmentToAskQuestionFragment(attributes)
            Navigation.findNavController(view).navigate(action)
        }
        fragment_detail_home.setOnClickListener {
            if (media_is_playing) media.start()
            val action = AnimeListDetailDirections.actionDetailFragmentToListFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun alertNotEnoughQuestions(view: View) {
        val action = AnimeListDetailDirections.actionDetailFragmentToLowQuestionCountFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun startQuiz(view: View) {
        val action = AnimeListDetailDirections.actionDetailFragmentToAnswerQuestionFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }
}
