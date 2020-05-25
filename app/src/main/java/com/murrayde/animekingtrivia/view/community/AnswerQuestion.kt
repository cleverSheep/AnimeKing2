@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingtrivia.view.community


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
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.model.community.CommunityQuestion
import com.murrayde.animekingtrivia.model.community.QuestionFactory
import com.murrayde.animekingtrivia.util.QuestionUtil
import com.murrayde.animekingtrivia.util.questionCount
import com.murrayde.animekingtrivia.view.community.list_anime.AnimeListDetailArgs
import com.murrayde.animekingtrivia.view.community.viewmodel.ResultsViewModel
import kotlinx.android.synthetic.main.fragment_answer_question.*
import timber.log.Timber


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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        vibration_is_enabled = sharedPreferences.getBoolean("vibration", true)
        results_view_model = ViewModelProvider(activity!!).get(ResultsViewModel::class.java)
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        QuestionFactory.RETRIEVE(attributes.titles.en
                ?: attributes.canonicalTitle, db, object : QuestionFactory.StatusCallback {
            // NOTE: Callback used to handle asynchronous calls to Firebase Firestore
            override fun onStatusCallback(list: ArrayList<CommunityQuestion>) {
                communityQuestions = list
                questions_list_argument = Array(list.size) { CommunityQuestion() }
                tv_answer_title.text = args.animeAttributes.titles.en
                        ?: args.animeAttributes.canonicalTitle

                val question_count = questionCount(communityQuestions.size, QuestionUtil.QUESTION_LIMIT)
                answer_question_score.text = "0/${results_view_model.getTotalQuestions()}"
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

        QuestionUtil.setUpQuestionWithImage(iv_answer_question, communityQuestions[question_track].image_url,
                tv_answer_question, communityQuestions[question_track].question)

        repeat(list_buttons.size) {
            list_buttons[it].text = random_questions.removeAt(0)
        }

        startTimer(communityQuestions, question_track++, view, list_buttons)
        buttonChoiceClick(list_buttons, communityQuestions, track)

        button_next_question.setOnClickListener {
            prepareForNextQuestion(communityQuestions, question_track++, view, list_buttons)
        }
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
                button_next_question.visibility = View.VISIBLE
                showTimeUpDialog(randomQuestions, new_question, view, list_buttons)
            }

            override fun onTick(time: Long) {
                answer_question_timer.text = "${time / 1000}s"
                current_time = time.toInt() / 1000
            }

        }.start()
    }

    private fun showTimeUpDialog(randomQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
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
            button_next_question.visibility = View.INVISIBLE
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
                    Timber.d("Time bonus incremented by $current_time")
                    val question_count = if (communityQuestions.size < QuestionUtil.QUESTION_LIMIT) communityQuestions.size else QuestionUtil.QUESTION_LIMIT
                    answer_question_score.text = "${current_score}/$question_count"
                    alertCorrectResponse(view)
                } else {
                    communityQuestions[question_track].setUserCorrectResponse(false)
                    alertWrongResponse(view, list_buttons, correct_response)
                }
                button_next_question.visibility = View.VISIBLE
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
        button.background = resources.getDrawable(com.murrayde.animekingtrivia.R.drawable.answer_wrong_background)
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
        if (media_is_playing) media_wrong.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    private fun prepareForNextQuestion(communityQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        if (media_is_playing) media_default.start()
        button_next_question.visibility = View.INVISIBLE
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
