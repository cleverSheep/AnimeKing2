@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingandroid.screen.game


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.murrayde.animekingandroid.R
import com.murrayde.animekingandroid.extensions.animateQuizItems
import com.murrayde.animekingandroid.extensions.hideView
import com.murrayde.animekingandroid.extensions.reverseAnimate
import com.murrayde.animekingandroid.extensions.showView
import com.murrayde.animekingandroid.model.community.CommunityQuestion
import com.murrayde.animekingandroid.screen.game_over.GameOverViewModel
import com.murrayde.animekingandroid.util.QuestionUtil
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
    private lateinit var gameOver_view_model: GameOverViewModel

    private lateinit var countDownTimer: CountDownTimer
    private var current_time = 0
    private var current_question = 1
    private var remaining_lives = 3

    private lateinit var question_correct: HashMap<Int, Boolean>
    private lateinit var questions_list_argument: Array<CommunityQuestion>
    private lateinit var vibrator: Vibrator
    private lateinit var animeTitle: String
    private lateinit var button_next_question: Button
    private lateinit var button_quit_game: Button

    private lateinit var handler: Handler

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        vibration_is_enabled = sharedPreferences.getBoolean("vibration", true)
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        gameOver_view_model = ViewModelProvider(requireActivity()).get(GameOverViewModel::class.java)
        button_next_question = view.findViewById(R.id.button_next_question)
        button_quit_game = view.findViewById(R.id.button_quit_game)
        handler = Handler()

        answerQuestionViewModel.getListOfQuestions().observe(requireActivity(), Observer { listQuestions ->
            communityQuestions = listQuestions
            questions_list_argument = Array(listQuestions.size) { CommunityQuestion() }
            gameOver_view_model.resetPoints(0)
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
        buttonChoiceClick(list_buttons, communityQuestions, track)
        button_next_question.setOnClickListener {
            transitionToNextQuestion(track, communityQuestions, view, list_buttons)
        }
        button_quit_game.setOnClickListener {
            navigateToResultsScreen(view)
        }
        startTimer(communityQuestions, track, view, list_buttons)
        tv_current_question.text = String.format(view.context.getString(R.string.current_question), current_question)
        AnimatorSet().apply {
            play(animateQuestion(tv_answer_question)).before(animateButtons(list_buttons))
            start()
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
                game_screen_timer.text = "$current_time"
            }

        }.start()
    }

    private fun showTimeUpDialog(randomQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view.findViewById<ViewGroup>(R.id.main_view_content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.time_up_layout, viewGroup, false)

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
            transitionToNextQuestion(track, randomQuestions, view, list_buttons)
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
                    communityQuestions[question_track].setUserCorrectResponse(true)
                    gameOver_view_model.updateTotalCorrect()
                    gameOver_view_model.incrementTimeBonus(current_time)
                    alertCorrectResponse(view, list_buttons, correct_response, communityQuestions, question_track, list_buttons[position])
                } else {
                    remaining_lives -= 1
                    tv_remaining_lives.text = remaining_lives.toString()
                    communityQuestions[question_track].setUserCorrectResponse(false)
                    alertWrongResponse(view, list_buttons, correct_response, question_track, communityQuestions)
                }
            }
        }
    }

    private fun disableAllButtons(list_buttons: ArrayList<Button>) {
        repeat(list_buttons.size) {
            list_buttons[it].isClickable = false
        }
    }

    private fun transitionToNextQuestion(track: Int, communityQuestions: List<CommunityQuestion>, view: View, list_buttons: ArrayList<Button>) {
        val new_question = track + 1
        current_question += 1
        currentQuestionAnimator.nextQuestion()
        prepareForNextQuestion(communityQuestions, new_question, view, list_buttons)
    }

    private fun prepareForNextQuestion(communityQuestions: List<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        if (media_is_playing) media_default.start()
        button_next_question.hideView()
        tv_answer_question.alpha = 0f
        repeat(list_buttons.size) { position ->
            list_buttons[position].setBackgroundColor(resources.getColor(R.color.color_white))
            list_buttons[position].setTextColor(resources.getColor(R.color.color_background_white))
            list_buttons[position].background = resources.getDrawable(R.drawable.answer_question_background)
            list_buttons[position].isClickable = true
            list_buttons[position].alpha = 0f
        }
        loadQuestions(communityQuestions, track, view, list_buttons)
    }

    private fun alertCorrectResponse(view: View, list_buttons: ArrayList<Button>, correct_response: String, communityQuestions: List<CommunityQuestion>, question_track: Int, correctButton: Button) {
        val button = view as Button
        tv_game_score.text = gameOver_view_model.getTotalCorrect().toString()
        if (media_is_playing) media_correct.start()
        button.background = resources.getDrawable(R.drawable.answer_correct_background)
        button.setTextColor(resources.getColor(R.color.color_white))
        val listOfButtonsToReverseAnimate = list_buttons.filter {
            it.text != correct_response
        }
        reverseAnimateButtons(listOfButtonsToReverseAnimate)
        handler.postDelayed({
            AnimatorSet().apply {
                playTogether(
                        tv_answer_question.reverseAnimate(),
                        correctButton.reverseAnimate()
                )
                duration = 200
                start()
            }
        }, 2500)
        handler.postDelayed({
            transitionToNextQuestion(question_track, communityQuestions, view, list_buttons)
        }, 3000)
    }

    private fun alertWrongResponse(view: View, list_buttons: ArrayList<Button>, correct_response: String, question_track: Int, communityQuestions: List<CommunityQuestion>) {
        if (remaining_lives <= 0) {
            navigateToResultsScreen(view)
            return
        }
        val button = view as Button
        if (media_is_playing) media_wrong.start()
        if (vibration_is_enabled) vibrator.vibrate(250)
        button.background = resources.getDrawable(com.murrayde.animekingandroid.R.drawable.answer_wrong_background)
        button.setTextColor(resources.getColor(R.color.color_white))
        repeat(list_buttons.size) { position ->
            if (list_buttons[position].text == correct_response) {
                list_buttons[position].background = resources.getDrawable(R.drawable.answer_correct_background)
                list_buttons[position].setTextColor(resources.getColor(R.color.color_background_white))
            }
        }
        val listOfButtonsToReverseAnimate = list_buttons.filter {
            it.text != correct_response
        }
        val correctButton = list_buttons.filter { it.text == correct_response }[0]
        reverseAnimateButtons(listOfButtonsToReverseAnimate)
        handler.postDelayed({
            AnimatorSet().apply {
                playTogether(
                        tv_answer_question.reverseAnimate(),
                        correctButton.reverseAnimate()
                )
                duration = 200
                start()
            }

        }, 2500)
        view.postDelayed({ transitionToNextQuestion(question_track, communityQuestions, view, list_buttons) }, 3000)
    }

    private fun listButtons(): ArrayList<Button> {
        val list_buttons = ArrayList<Button>()
        list_buttons.add(button_choice_one)
        list_buttons.add(button_choice_two)
        list_buttons.add(button_choice_three)
        list_buttons.add(button_choice_four)
        return list_buttons
    }

    private fun animateQuestion(question: TextView): Animator {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.25f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.25f, 1f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

        return ObjectAnimator.ofPropertyValuesHolder(question, scaleX, scaleY, alpha).apply {
            duration = 450
            interpolator = OvershootInterpolator(1.5f)
        }

    }

    private fun animateButtons(list_buttons: ArrayList<Button>): AnimatorSet {
        val firstButton = list_buttons[0]
        val secondButton = list_buttons[1]
        val thirdButton = list_buttons[2]
        val fourthButton = list_buttons[3]
        return AnimatorSet().apply {
            playSequentially(
                    firstButton.animateQuizItems(),
                    secondButton.animateQuizItems(),
                    thirdButton.animateQuizItems(),
                    fourthButton.animateQuizItems()
            )
            duration = 215
            interpolator = OvershootInterpolator()
        }
    }

    private fun reverseAnimateButtons(list_buttons: List<Button>): AnimatorSet {
        val firstButton = list_buttons[0]
        val secondButton = list_buttons[1]
        val thirdButton = list_buttons[2]
        return AnimatorSet().apply {
            playTogether(
                    firstButton.reverseAnimate(),
                    secondButton.reverseAnimate(),
                    thirdButton.reverseAnimate()
            )
            duration = 200
            startDelay = 1000
            start()
        }
    }

    override fun onStop() {
        super.onStop()
        if (countDownTimer != null) countDownTimer.cancel()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        media_default.release()
        media_correct.release()
        media_wrong.release()
    }
}
