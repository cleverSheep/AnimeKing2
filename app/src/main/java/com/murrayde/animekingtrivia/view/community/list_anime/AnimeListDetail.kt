@file:Suppress("PropertyName", "PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingtrivia.view.community.list_anime

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.model.community.QuestionFactory
import com.murrayde.animekingtrivia.util.ImageUtil
import com.murrayde.animekingtrivia.view.community.viewmodel.AnimeDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class AnimeListDetail : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private lateinit var questionFactory: QuestionFactory
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animeDetailViewModel: AnimeDetailViewModel

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
        animeDetailViewModel = ViewModelProvider(this).get(AnimeDetailViewModel::class.java)

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
        fragment_detail_multiplayer.setOnClickListener {
            Toast.makeText(activity!!, R.string.coming_soon, Toast.LENGTH_SHORT).show()
        }

        val anime_title = attributes.titles.en ?: attributes.canonicalTitle

        fragment_detail_join.setOnClickListener {
            if (userIsInCommunity()) {
                Toast.makeText(activity, "Already joined this community.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            joinCommunity()
            animeDetailViewModel.incrementMemberCount(anime_title)
            Toast.makeText(activity, "Joined $anime_title community", Toast.LENGTH_SHORT).show()
        }

        animeDetailViewModel.getMemberCount(anime_title).observe(viewLifecycleOwner, Observer<Long> {
            fragment_detail_member_count.text = "$it member(s)"
        })
        animeDetailViewModel.getQuestionCount(anime_title).observe(viewLifecycleOwner, Observer<Long> {
            fragment_detail_question_count.text = "$it question(s)"
        })

    }

    private fun userIsInCommunity(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        val defaultValue = false
        return sharedPref.getBoolean(getString(R.string.community), defaultValue)
    }

    private fun joinCommunity() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.community), true)
            commit()
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
