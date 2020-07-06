@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.questions


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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.model.community.QuestionFactory
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.util.removeForwardSlashes
import com.murrayde.animekingmobile.view.community.quiz_results.ResultsViewModel
import kotlinx.android.synthetic.main.fragment_answer_question.*


class AnswerQuestion : Fragment() {

    private val args: AnswerQuestionArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var media_default: MediaPlayer
    private lateinit var media_correct: MediaPlayer
    private lateinit var media_wrong: MediaPlayer
    private var media_is_playing = true
    private var vibration_is_enabled = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var results_view_model: ResultsViewModel

    private var current_score: Int = 0
    private lateinit var countDownTimer: CountDownTimer
    private var current_time = 0

    private lateinit var question_correct: HashMap<Int, Boolean>
    private lateinit var questions_list_argument: Array<CommunityQuestion>
    private lateinit var vibrator: Vibrator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_answer_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        var communityQuestions: ArrayList<CommunityQuestion>
        question_correct = HashMap()

        media_default = create(activity, R.raw.button_click_sound_effect)
        media_correct = create(activity, R.raw.button_click_correct)
        media_wrong = create(activity, R.raw.button_click_wrong)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        vibration_is_enabled = sharedPreferences.getBoolean("vibration", true)
        results_view_model = ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        QuestionFactory.RETRIEVE(removeForwardSlashes(attributes.titles.en), db, object : QuestionFactory.StatusCallback {
            // NOTE: Callback used to handle asynchronous calls to Firebase Firestore
            override fun onStatusCallback(list: ArrayList<CommunityQuestion>) {
                communityQuestions = list
                questions_list_argument = Array(list.size) { CommunityQuestion() }
                results_view_model.resetPoints(0)
                loadQuestions(communityQuestions, 0, view, listButtons())
            }
        })
    }

    // TODO: WRITE TEST FOR loadQuestions(...)
    private fun loadQuestions(communityQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        // NOTE: Make sure to explicitly return or else the remaining lines of code will be executed
        if (track >= communityQuestions.size) {
            navigateToResultsScreen(view)
            return
        }
        var question_track = track
        questions_list_argument[question_track] = communityQuestions[question_track]
        val random_questions: ArrayList<String> = communityQuestions[question_track].multiple_choice.shuffled() as ArrayList<String>

        repeat(list_buttons.size) {
            list_buttons[it].text = random_questions.removeAt(0)
        }

        startTimer(communityQuestions, question_track++, view, list_buttons)
        buttonChoiceClick(list_buttons, communityQuestions, track)
    }

    private fun navigateToResultsScreen(view: View) {
        val action = AnswerQuestionDirections.actionAnswerQuestionFragmentToViewResults(questions_list_argument, args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun startTimer(randomQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        val new_question = track + 1
        countDownTimer = object : CountDownTimer(QuestionUtil.QUESTION_TIMER, 1000) {
            override fun onFinish() {
                disableAllButtons(list_buttons)
                showTimeUpDialog(randomQuestions, new_question, view, list_buttons)
            }

            override fun onTick(time: Long) {
                current_time = time.toInt() / 1000
                tv_timer.text = current_time.toString()
                timer_progressbar.setProgress(current_time, true)
            }

        }.start()
    }

    private fun showTimeUpDialog(randomQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
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
        }
        alertDialog.show()
    }

    private fun buttonChoiceClick(list_buttons: ArrayList<Button>, communityQuestions: ArrayList<CommunityQuestion>, question_track: Int) {
        val correct_response = communityQuestions[question_track].multiple_choice[0]
        repeat(list_buttons.size) { position ->
            list_buttons[position].setOnClickListener { view ->
                countDownTimer.cancel()
                disableAllButtons(list_buttons)
                if (list_buttons[position].text == correct_response) {
                    communityQuestions[question_track].setUserCorrectResponse(true)
                    results_view_model.updateTotalCorrect(++current_score)
                    results_view_model.incrementTimeBonus(current_time)
                    alertCorrectResponse(view)
                } else {
                    communityQuestions[question_track].setUserCorrectResponse(false)
                    alertWrongResponse(view, list_buttons, correct_response)
                }
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
                list_buttons[position].setTextColor(resources.getColor(R.color.color_white))
            }
        }
    }

    private fun alertCorrectResponse(view: View) {
        val button = view as Button
        if (media_is_playing) media_correct.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    private fun prepareForNextQuestion(communityQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        if (media_is_playing) media_default.start()
        repeat(list_buttons.size) { position ->
            list_buttons[position].setBackgroundColor(resources.getColor(R.color.color_white))
            list_buttons[position].setTextColor(resources.getColor(R.color.color_black))
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
