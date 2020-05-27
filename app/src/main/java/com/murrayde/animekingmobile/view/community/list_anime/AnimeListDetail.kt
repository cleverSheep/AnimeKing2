@file:Suppress("PropertyName", "PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_anime

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.model.community.QuestionFactory
import com.murrayde.animekingmobile.util.ImageUtil
import com.murrayde.animekingmobile.util.removeForwardSlashes
import com.murrayde.animekingmobile.view.community.viewmodel.AnimeDetailViewModel
import com.murrayde.animekingmobile.view.community.viewmodel.ResultsViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber


class AnimeListDetail : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private lateinit var questionFactory: QuestionFactory
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animeDetailViewModel: AnimeDetailViewModel
    private lateinit var resultsViewModel: ResultsViewModel
    private lateinit var app_language: String
    private lateinit var fragment_detail_question_count: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_detail)
        toolbar.setupWithNavController(findNavController())
        fragment_detail_question_count = view.findViewById(R.id.fragment_detail_question_count)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        animeDetailViewModel = ViewModelProvider(this).get(AnimeDetailViewModel::class.java)
        resultsViewModel = ViewModelProvider(activity!!).get(ResultsViewModel::class.java)

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
        handleClickLogic(view)

        val anime_title = attributes.titles.en ?: attributes.canonicalTitle

        animeDetailViewModel.getQuestionCount(removeForwardSlashes(anime_title)).observe(activity!!, Observer<Long> {
            fragment_detail_question_count.text = "$it question(s)"
            resultsViewModel.setTotalQuestions(it.toInt())
            Timber.d("Total questions: ${it.toInt()}")
        })

    }

    private fun alertNotEnoughQuestions(view: View) {
        val action = AnimeListDetailDirections.actionDetailFragmentToLowQuestionCountFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun startQuiz(view: View) {
        val action = AnimeListDetailDirections.actionDetailFragmentToAnswerQuestionFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun handleClickLogic(view: View) {
        fragment_detail_take_quiz.setOnClickListener {
            if (media_is_playing) media.start()
            it.isEnabled = false
            questionFactory.hasEnoughQuestions(removeForwardSlashes(args.animeAttributes.titles.en)
                    ?: args.animeAttributes.canonicalTitle, object : QuestionFactory.QuestionCountCallback {
                override fun onQuestionCountCallback(hasEnoughQuestions: Boolean) {
                    if (hasEnoughQuestions) {
                        startQuiz(it)
                    } else alertNotEnoughQuestions(it)
                }
            })
        }
        fragment_detail_ask_question.setOnClickListener {
            if (media_is_playing) media.start()
            val action = AnimeListDetailDirections.actionDetailFragmentToAskQuestionFragment(args.animeAttributes)
            Navigation.findNavController(view).navigate(action)
        }
        fragment_detail_home.setOnClickListener {
            if (media_is_playing) media.start()
            val action = AnimeListDetailDirections.actionDetailFragmentToListFragment()
            Navigation.findNavController(view).navigate(action)
        }
        fragment_detail_multiplayer.setOnClickListener {
            Toast.makeText(activity!!, R.string.coming_soon, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }
}
