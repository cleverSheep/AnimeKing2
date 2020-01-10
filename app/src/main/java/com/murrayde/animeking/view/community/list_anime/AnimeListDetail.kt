@file:Suppress("PropertyName", "PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime

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
import com.bumptech.glide.Glide
import com.murrayde.animeking.R
import com.murrayde.animeking.model.community.QuestionFactory
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber

class AnimeListDetail : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private lateinit var questionFactory: QuestionFactory
    private lateinit var media: MediaPlayer

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

        tv_ask_question.text = attributes.titles.en ?: attributes.canonicalTitle
        tv_description.text = attributes.synopsis
        Glide.with(this)
                .load(attributes.posterImage.original)
                .placeholder(R.drawable.feature_graphic)
                .dontAnimate()
                .into(iv_image)

        questionFactory = QuestionFactory()
        button_take_quiz.setOnClickListener {
            media.start()
            questionFactory.hasEnoughQuestions(attributes.titles.en
                    ?: attributes.canonicalTitle, object : QuestionFactory.QuestionCountCallback {
                override fun onQuestionCountCallback(hasEnoughQuestions: Boolean) {
                    if (hasEnoughQuestions) {
                        startQuiz(it)
                    } else alertNotEnoughQuestions(it)
                }
            })
        }
        button_ask_question.setOnClickListener {
            media.start()
            val action = AnimeListDetailDirections.actionDetailFragmentToAskQuestionFragment(attributes)
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
        Timber.d("Media release, fragment destroyed")
    }
}
