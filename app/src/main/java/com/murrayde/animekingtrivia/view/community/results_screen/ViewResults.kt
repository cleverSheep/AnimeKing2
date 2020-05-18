@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.view.community.results_screen


import android.content.SharedPreferences
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.extensions.hideView
import com.murrayde.animekingtrivia.extensions.showView
import com.murrayde.animekingtrivia.model.community.AnimeAttributes.Attributes
import com.murrayde.animekingtrivia.model.community.QuestionFactory
import com.murrayde.animekingtrivia.util.QuestionUtil
import com.murrayde.animekingtrivia.view.community.list_anime.AnimeListDetailArgs
import com.murrayde.animekingtrivia.view.community.list_anime.AnimeListDetailDirections
import com.murrayde.animekingtrivia.view.community.results_screen.review_questions.ReviewQuestionsAdapter
import com.murrayde.animekingtrivia.view.community.viewmodel.ResultsViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_view_results.*
import kotlinx.android.synthetic.main.fragment_view_results_bottom_sheet.*
import org.w3c.dom.Text
import timber.log.Timber


class ViewResults : Fragment() {

    private val resultsArgs: ViewResultsArgs by navArgs()
    private lateinit var linearLayoutBottomSheet: LinearLayout
    private lateinit var resultsViewModel: ResultsViewModel
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_results, container, false)
        resultsViewModel = ViewModelProvider(activity!!).get(ResultsViewModel::class.java)
        val results_correct_percentage = view.findViewById<TextView>(R.id.results_correct_percentage)
        val results_time_bonus_points = view.findViewById<TextView>(R.id.results_time_bonus_points)
        val results_total_points = view.findViewById<TextView>(R.id.results_total_points_tv)
        val quote = view.findViewById<TextView>(R.id.quote)
        val game_over = view.findViewById<TextView>(R.id.game_over)

        val question_count = if (resultsViewModel.getTotalQuestions() < QuestionUtil.QUESTION_LIMIT) resultsViewModel.getTotalQuestions() else QuestionUtil.QUESTION_LIMIT
        results_correct_percentage.text = "${resultsViewModel.totalCorrect()} out of $question_count questions correct"
        results_time_bonus_points.text = "${resultsViewModel.totalTimeBonus().toString()} pts"
        results_total_points.text = "${resultsViewModel.totalPoints().toString()} pts"
        quote.text = if (resultsViewModel.positiveMessage()) resources.getString(R.string.won_quote) else resources.getString(R.string.lost_quote)
        game_over.text = if (resultsViewModel.positiveMessage()) resources.getString(R.string.good_game) else resources.getString(R.string.game_over)

        Timber.d("Positive message: ${resultsViewModel.positiveMessage()}")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list_questions = resultsArgs.listQuestions
        val review_questions_adapter = ReviewQuestionsAdapter(activity!!, list_questions!!)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)

        bottom_sheet_rv.adapter = review_questions_adapter
        bottom_sheet_rv.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)

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
                    view_results_upper.hideView()
                }
                if (offset == 0f) {
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
            val direction = ViewResultsDirections.actionViewResultsToDetailFragment(resultsArgs.animeAttributes)
            Navigation.findNavController(v).navigate(direction)
        }
    }

    private fun prepareQuiz(attributes: Attributes, view: View) {
        if (media_is_playing) media.start()
        startQuiz(view)
    }

    private fun startQuiz(view: View) {
        val action = ViewResultsDirections.actionViewResultsToAnswerQuestionFragment(resultsArgs.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }
}
