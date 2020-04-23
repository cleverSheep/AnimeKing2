@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingtrivia.view.random


import android.content.DialogInterface
import android.content.SharedPreferences
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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.model.random.Result
import com.murrayde.animekingtrivia.util.QuestionUtil
import kotlinx.android.synthetic.main.fragment_random_questions.*
import timber.log.Timber

class AnswerRandomQuestions : Fragment() {

    private lateinit var media_default: MediaPlayer
    private lateinit var media_correct: MediaPlayer
    private lateinit var media_wrong: MediaPlayer
    private var current_score: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_random_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        media_default = create(activity!!, R.raw.button_click_sound_effect)
        media_correct = create(activity, R.raw.button_click_correct)
        media_wrong = create(activity, R.raw.button_click_wrong)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)

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
        val answer_choices = shuffleAnswerChoices(randomQuestions, question_track)

        val question = Html.fromHtml(randomQuestions[question_track].question)
        random_question_tv.text = question

        repeat(list_buttons.size) { position ->
            list_buttons[position].text = Html.fromHtml(answer_choices.removeAt(0))
        }

        startTimer(randomQuestions, question_track, view, list_buttons)
        buttonChoiceClick(list_buttons, randomQuestions, track)

        random_question_next_btn.setOnClickListener {
            prepareForNextQuestion(randomQuestions, ++question_track, view, list_buttons)
        }
    }

    private fun navigateBackHome(view: View) {
        val action = AnswerRandomQuestionsDirections.actionAnswerRandomQuestionsToHome()
        Navigation.findNavController(view).navigate(action)
    }

    private fun shuffleAnswerChoices(randomQuestions: ArrayList<Result>, current_question: Int): ArrayList<String> {
        val answer_choices = ArrayList<String>()
        answer_choices.addAll(randomQuestions[current_question].incorrect_answers)
        answer_choices.add(randomQuestions[current_question].correct_answer)
        answer_choices.shuffle()
        return answer_choices
    }

    private fun startTimer(randomQuestions: ArrayList<Result>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        countDownTimer = object : CountDownTimer(QuestionUtil.QUESTION_TIMER, 1000) {
            override fun onFinish() {
                var current_question = track
                disableAllButtons(list_buttons)
                showTimeUpDialog(randomQuestions, ++current_question, view, list_buttons)
            }

            override fun onTick(p0: Long) {
                Timber.d(p0.toString())
                random_time_tv.text = "${p0 / 1000}s"
            }

        }.start()
    }

    private fun showTimeUpDialog(randomQuestions: ArrayList<Result>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view.findViewById<ViewGroup>(R.id.main_view_content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(activity!!).inflate(R.layout.time_up_layout, viewGroup, false)

        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(activity!!)


        val button = dialogView.findViewById<Button>(R.id.time_up_next_question)

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView).setPositiveButton("") { _: DialogInterface, _: Int -> }
        builder.setNegativeButton("") { _: DialogInterface, _: Int -> }

        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        button.setOnClickListener {
            alertDialog.dismiss()
            loadQuestions(randomQuestions, track, view, list_buttons)
        }
        alertDialog.show()
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

    private fun disableAllButtons(list_buttons: ArrayList<Button>) {
        repeat(list_buttons.size) {
            list_buttons[it].isClickable = false
        }
    }

    private fun alertWrongResponse(view: View, list_buttons: ArrayList<Button>, correct_response: Spanned) {
        val button = view as Button
        if(media_is_playing) media_wrong.start()
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
        if(media_is_playing) media_correct.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    private fun prepareForNextQuestion(randomQuestions: ArrayList<Result>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        if(media_is_playing) media_default.start()
        random_question_next_btn.visibility = View.INVISIBLE
        repeat(list_buttons.size) { position ->
            list_buttons[position].setBackgroundColor(resources.getColor(R.color.color_white))
            list_buttons[position].setTextColor(resources.getColor(R.color.color_black))
            list_buttons[position].background = resources.getDrawable(R.drawable.answer_question_background)
            list_buttons[position].isClickable = true
        }
        loadQuestions(randomQuestions, track, view, list_buttons)
    }

    private fun listButtons(): ArrayList<Button> {
        val list_buttons = ArrayList<Button>()
        list_buttons.add(random_question_btn1)
        list_buttons.add(random_question_btn2)
        list_buttons.add(random_question_btn3)
        list_buttons.add(random_question_btn4)
        return list_buttons
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        media_default.release()
        media_correct.release()
        media_wrong.release()
    }


}
