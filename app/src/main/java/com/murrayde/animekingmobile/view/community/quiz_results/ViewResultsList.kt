@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.quiz_results


import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.hideView
import com.murrayde.animekingmobile.extensions.showView
import com.murrayde.animekingmobile.model.community.AnimeAttributes.Attributes
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.util.questionCount
import com.murrayde.animekingmobile.view.community.quiz_results.review_questions.ReviewQuestionsAdapter
import kotlinx.android.synthetic.main.fragment_view_results.*
import kotlinx.android.synthetic.main.fragment_view_results_bottom_sheet.*
import timber.log.Timber


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
        val results_correct_percentage = view.findViewById<TextView>(R.id.results_correct_percentage)
        val results_time_bonus_points = view.findViewById<TextView>(R.id.results_time_bonus_points)
        val results_total_points = view.findViewById<TextView>(R.id.results_total_points_tv)
        val quote = view.findViewById<TextView>(R.id.quote)
        val game_over = view.findViewById<TextView>(R.id.game_over)

        val question_count = questionCount(resultsViewModel.getTotalQuestions(), QuestionUtil.QUESTION_LIMIT)
        results_correct_percentage.text = String.format(view.context.getString(R.string.questions_correct_count), resultsViewModel.totalCorrect(), question_count)
        results_time_bonus_points.text = String.format(view.context.getString(R.string.total_time_bonus), resultsViewModel.totalTimeBonus())
        results_total_points.text = String.format(view.context.getString(R.string.total_points), resultsViewModel.totalPoints())
        quote.text = if (resultsViewModel.positiveMessage()) resources.getString(R.string.won_quote) else resources.getString(R.string.lost_quote)
        game_over.text = if (resultsViewModel.positiveMessage()) resources.getString(R.string.good_game) else resources.getString(R.string.game_over)

        Timber.d("Positive message: ${resultsViewModel.positiveMessage()}")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list_questions = resultsArgs.listQuestions
        val review_questions_adapter = ReviewQuestionsAdapter(requireActivity(), list_questions!!)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)


        bottom_sheet_rv.adapter = review_questions_adapter
        bottom_sheet_rv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        linearLayoutBottomSheet = view.findViewById(R.id.results_bottom_sheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomSheet)

        results_try_again.setOnClickListener { view ->
            prepareQuiz(resultsArgs.animeAttributes, view)
        }

        results_toggle_button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            else bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        //results_total_points_tv.text = resultsViewModel.totalPoints().toString()

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, offset: Float) {
                Timber.d("Bottom sheet progress: $offset")

                if (offset > -0f) {
                    done_reviewing.showView()
                    view_results_upper.hideView()
                }/*
                if (offset < 0.5f) {
                    done_reviewing.hideView()
                }*/
                if (offset == 0f) {
                    done_reviewing.hideView()
                    view_results_upper.showView()
                }
            }

            override fun onStateChanged(view: View, new_state: Int) {
                if (new_state == BottomSheetBehavior.STATE_EXPANDED) {
                    results_toggle_button.isChecked = true
                } else if (new_state == BottomSheetBehavior.STATE_COLLAPSED) {
                    results_toggle_button.isChecked = false
                }
            }

        })

        results_quit_game.setOnClickListener { v ->
            val direction = ViewResultsListDirections.actionViewResultsToDetailFragment(resultsArgs.animeAttributes)
            Navigation.findNavController(v).navigate(direction)
        }
    }

    private fun prepareQuiz(attributes: Attributes, view: View) {
        if (media_is_playing) media.start()
        startQuiz(view)
    }

    private fun startQuiz(view: View) {
        val action = ViewResultsListDirections.actionViewResultsToAnswerQuestionFragment(resultsArgs.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }
}
