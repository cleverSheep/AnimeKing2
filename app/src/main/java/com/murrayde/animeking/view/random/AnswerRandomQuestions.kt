@file:Suppress("LocalVariableName")

package com.murrayde.animeking.view.random


import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.murrayde.animeking.R
import com.murrayde.animeking.model.random.Result
import com.murrayde.animeking.util.QuestionUtil
import kotlinx.android.synthetic.main.fragment_answer_question.*
import kotlinx.android.synthetic.main.fragment_random_questions.*
import kotlinx.android.synthetic.main.fragment_random_questions.random_question_score_tv
import timber.log.Timber

class AnswerRandomQuestions : Fragment() {

    private lateinit var media_default: MediaPlayer
    private lateinit var media_correct: MediaPlayer
    private lateinit var media_wrong: MediaPlayer
    private var current_score: Int = 0
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        media_default = create(activity, R.raw.button_click_sound_effect)
        media_correct = create(activity, R.raw.button_click_correct)
        media_wrong = create(activity, R.raw.button_click_wrong)

        var randomQuestions: ArrayList<Result>

        val randomQuestionsViewModel = ViewModelProvider(this).get(RandomQuestionsViewModel::class.java)
        randomQuestionsViewModel.getQuestionSet().observe(this, Observer {
            randomQuestions = it as ArrayList<Result>
            loadQuestions(randomQuestions, 0, view, listButtons())
        })

    }

    private fun loadQuestions(randomQuestions: ArrayList<Result>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        var question_track = track

        // NOTE: Make sure to explicitly return or else the remaining lines of code will be executed
        if (question_track >= randomQuestions.size) {
            navigateBackHome(view)
            return
        }
        val question_answer = ArrayList<String>()
        question_answer.addAll(randomQuestions[question_track].incorrect_answers)
        question_answer.add(randomQuestions[question_track].correct_answer)
        question_answer.shuffle()

        val question = Html.fromHtml(randomQuestions[question_track].question)
        random_question_tv.text = question

        repeat(list_buttons.size) {
            list_buttons[it].text = Html.fromHtml(question_answer.removeAt(0))
        }
        startTimer()
        buttonChoiceClick(list_buttons, randomQuestions, question_track)

        random_question_next_btn.setOnClickListener {
            media_default.start()
            random_question_next_btn.visibility = View.INVISIBLE
            repeat(list_buttons.size) {
                list_buttons[it].setBackgroundColor(resources.getColor(R.color.color_white))
                list_buttons[it].setTextColor(resources.getColor(R.color.color_grey))
                list_buttons[it].background = resources.getDrawable(R.drawable.answer_question_background)
                list_buttons[it].isClickable = true
            }
            loadQuestions(randomQuestions, ++question_track, view, list_buttons)
        }
    }


    private fun navigateBackHome(view: View) {
        val action = AnswerRandomQuestionsDirections.actionRandomQuestionsToLandingScreen2()
        Navigation.findNavController(view).navigate(action)
    }

    private fun buttonChoiceClick(list_buttons: ArrayList<Button>, randomQuestions: ArrayList<Result>, question_track: Int) {
        val correct_response = Html.fromHtml(randomQuestions[question_track].correct_answer)
        repeat(list_buttons.size) { position ->
            list_buttons[position].setOnClickListener { view ->
                countDownTimer.cancel()
                disableAllButtons(list_buttons)
                if (list_buttons[position].text == correct_response) {
                    current_score++
                    random_question_score_tv.text = "${current_score}/10"
                    alertCorrectResponse(view)
                } else alertWrongResponse(view, list_buttons, correct_response)
                random_question_next_btn.visibility = View.VISIBLE
            }
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(QuestionUtil.QUESTION_TIMER, 1000) {
            override fun onFinish() {
                this.cancel()
            }

            override fun onTick(p0: Long) {
                Timber.d(p0.toString())
                random_time_tv.text = "${p0 / 1000}s"
            }

        }.start()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()
    }

    private fun disableAllButtons(list_buttons: ArrayList<Button>) {
        repeat(list_buttons.size) {
            list_buttons[it].isClickable = false
        }
    }

    private fun alertWrongResponse(view: View, list_buttons: ArrayList<Button>, correct_response: Spanned) {
        val button = view as Button
        media_wrong.start()
        button.background = resources.getDrawable(R.drawable.answer_wrong_background)
        button.setTextColor(resources.getColor(R.color.color_white))
        repeat(list_buttons.size) { position ->
            if (list_buttons[position].text == correct_response) {
                list_buttons[position].background = resources.getDrawable(R.drawable.answer_correct_background)
                list_buttons[position].setTextColor(resources.getColor(R.color.color_white))
            }
        }
    }

    private fun alertCorrectResponse(view: View) {
        val button = view as Button
        media_correct.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    private fun listButtons(): ArrayList<Button> {
        val list_buttons = ArrayList<Button>()
        list_buttons.add(random_question_btn1)
        list_buttons.add(random_question_btn2)
        list_buttons.add(random_question_btn3)
        list_buttons.add(random_question_btn4)
        return list_buttons
    }

    override fun onDestroy() {
        super.onDestroy()
        media_default.release()
        media_correct.release()
        media_wrong.release()
    }


}
