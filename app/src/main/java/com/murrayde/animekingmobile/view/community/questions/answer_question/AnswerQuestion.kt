@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.questions.answer_question


import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.hideView
import com.murrayde.animekingmobile.extensions.showView
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.view.community.quiz_results.ResultsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.answer_question_screen.*

@AndroidEntryPoint
class AnswerQuestion : Fragment() {

    private val args: AnswerQuestionArgs by navArgs()

    private lateinit var media_default: MediaPlayer
    private lateinit var media_correct: MediaPlayer
    private lateinit var media_wrong: MediaPlayer
    private var media_is_playing = true
    private var vibration_is_enabled = true
    private lateinit var sharedPreferences: SharedPreferences
    private val answerQuestionViewModel: AnswerQuestionViewModel by viewModels()
    private val results_view_model: ResultsViewModel by viewModels()

    private var current_score: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private var current_time = 0

    private lateinit var question_correct: HashMap<Int, Boolean>
    private lateinit var questions_list_argument: Array<CommunityQuestion>
    private lateinit var vibrator: Vibrator
    private lateinit var animeTitle: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        animeTitle = if (args.animeAttributes.titles.en != null) args.animeAttributes.titles.en else args.animeAttributes.canonicalTitle
        answerQuestionViewModel.getQuestions(animeTitle)
        return inflater.inflate(R.layout.answer_question_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var communityQuestions: List<CommunityQuestion>
        question_correct = HashMap()

        media_default = create(activity, R.raw.button_click_sound_effect)
        media_correct = create(activity, R.raw.button_click_correct)
        media_wrong = create(activity, R.raw.button_click_wrong)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        vibration_is_enabled = sharedPreferences.getBoolean("vibration", true)
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        answerQuestionViewModel.getListOfQuestions().observe(requireActivity(), Observer { listQuestions ->
            communityQuestions = listQuestions
            questions_list_argument = Array(listQuestions.size) { CommunityQuestion() }
            results_view_model.resetPoints(0)
            loadQuestions(communityQuestions, 0, view, listButtons())
        })
    }

    private fun loadQuestions(communityQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        // NOTE: Make sure to explicitly return or else the remaining lines of code will be executed
        if (track >= communityQuestions.size) {
            navigateToResultsScreen(view)
            return
        }
        questions_list_argument[track] = communityQuestions[track]
        val random_questions: ArrayList<String> = communityQuestions[track].multiple_choice.shuffled() as ArrayList<String>

        tv_answer_question.text = communityQuestions[track].question
        repeat(list_buttons.size) {
            list_buttons[it].text = random_questions.removeAt(0)
        }

        startTimer(communityQuestions, track, view, list_buttons)
        buttonChoiceClick(list_buttons, communityQuestions, track)

        button_next_question.setOnClickListener {
            val new_question = track + 1
            prepareForNextQuestion(communityQuestions, new_question, view, list_buttons)
        }
    }

    private fun navigateToResultsScreen(view: View) {
        val action = AnswerQuestionDirections.actionAnswerQuestionFragmentToViewResults(questions_list_argument, args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun startTimer(randomQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        val new_question = track + 1
        countDownTimer = object : CountDownTimer(QuestionUtil.QUESTION_TIMER, 1000) {
            override fun onFinish() {
                disableAllButtons(list_buttons)
                button_next_question.showView()
                showTimeUpDialog(randomQuestions, new_question, view, list_buttons)
            }

            override fun onTick(time: Long) {
                current_time = time.toInt() / 1000
                tv_timer.text = current_time.toString()
                timer_progressbar.setProgress(current_time, true)
            }

        }.start()
    }

    private fun showTimeUpDialog(randomQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view.findViewById<ViewGroup>(R.id.main_view_content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.time_up_layout, viewGroup, false)

        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(requireActivity())

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
            button_next_question.hideView()
        }
        alertDialog.show()
    }

    private fun buttonChoiceClick(list_buttons: ArrayList<Button>, communityQuestions: List<CommunityQuestion>, question_track: Int) {
        val correct_response = communityQuestions[question_track].multiple_choice[0]
        repeat(list_buttons.size) { position ->
            list_buttons[position].setOnClickListener { view ->
                countDownTimer.cancel()
                disableAllButtons(list_buttons)
                if (list_buttons[position].text == correct_response) {
                    tv_score.text = "${++current_score * 10}"
                    communityQuestions[question_track].setUserCorrectResponse(true)
/*                    results_view_model.updateTotalCorrect(++current_score)
                    results_view_model.incrementTimeBonus(current_time)*/
                    alertCorrectResponse(view)
                } else {
                    communityQuestions[question_track].setUserCorrectResponse(false)
                    alertWrongResponse(view, list_buttons, correct_response)
                }
                button_next_question.showView()
            }
        }
    }

    private fun disableAllButtons(list_buttons: ArrayList<Button>) {
        repeat(list_buttons.size) {
            list_buttons[it].isClickable = false
        }
    }

    private fun alertWrongResponse(view: View, list_buttons: ArrayList<Button>, correct_response: String) {
        val button = view as Button
        if (media_is_playing) media_wrong.start()
        if (vibration_is_enabled) vibrator.vibrate(250)
        button.background = resources.getDrawable(com.murrayde.animekingmobile.R.drawable.answer_wrong_background)
        button.setTextColor(resources.getColor(R.color.color_white))
        repeat(list_buttons.size) { position ->
            if (list_buttons[position].text == correct_response) {
                list_buttons[position].background = resources.getDrawable(R.drawable.answer_correct_background)
                list_buttons[position].setTextColor(resources.getColor(R.color.color_background_white))
            }
        }
    }

    private fun alertCorrectResponse(view: View) {
        val button = view as Button
        if (media_is_playing) media_correct.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    private fun prepareForNextQuestion(communityQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        if (media_is_playing) media_default.start()
        button_next_question.hideView()
        repeat(list_buttons.size) { position ->
            list_buttons[position].setBackgroundColor(resources.getColor(R.color.color_white))
            list_buttons[position].setTextColor(resources.getColor(R.color.color_background_white))
            list_buttons[position].background = resources.getDrawable(R.drawable.answer_question_background)
            list_buttons[position].isClickable = true
        }
        loadQuestions(communityQuestions, track, view, list_buttons)
    }

    private fun listButtons(): ArrayList<Button> {
        val list_buttons = ArrayList<Button>()
        list_buttons.add(button_choice_one)
        list_buttons.add(button_choice_two)
        list_buttons.add(button_choice_three)
        list_buttons.add(button_choice_four)
        return list_buttons
    }

    override fun onDestroy() {
        super.onDestroy()
        media_default.release()
        media_correct.release()
        media_wrong.release()
    }
}
