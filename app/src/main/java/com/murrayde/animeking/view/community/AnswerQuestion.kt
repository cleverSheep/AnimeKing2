@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animeking.view.community


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animeking.R
import com.murrayde.animeking.model.community.CommunityQuestion
import com.murrayde.animeking.model.community.QuestionFactory
import com.murrayde.animeking.view.community.list_anime.AnimeListDetailArgs
import kotlinx.android.synthetic.main.fragment_answer_question.*

class AnswerQuestion : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var media_default: MediaPlayer
    private lateinit var media_correct: MediaPlayer
    private lateinit var media_wrong: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_answer_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        var communityQuestions: ArrayList<CommunityQuestion>
        media_default = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_correct = MediaPlayer.create(activity, R.raw.button_click_correct)
        media_wrong = MediaPlayer.create(activity, R.raw.button_click_wrong)

        QuestionFactory.RETRIEVE(attributes.titles.en
                ?: attributes.canonicalTitle, db, object : QuestionFactory.StatusCallback {
            // NOTE: Crate your own callback to handle asynchronous calls to FB Firestore
            override fun onStatusCallback(list: ArrayList<CommunityQuestion>) {
                communityQuestions = list
                tv_answer_title.text = args.animeAttributes.titles.en
                        ?: args.animeAttributes.canonicalTitle
                loadQuestions(communityQuestions, 0, view, listButtons())
            }
        })
    }

    // TODO: WRITE TEST FOR loadQuestions(...)
    private fun loadQuestions(communityQuestions: ArrayList<CommunityQuestion>, track: Int, view: View, list_buttons: ArrayList<Button>) {
        // NOTE: Make sure to explicitly return or else the remaining lines of code will be executed
        if (track >= communityQuestions.size) {
            navigateBackToDetail(view)
            return
        }
        var question_track = track
        val random_questions: ArrayList<String> = communityQuestions[question_track].multiple_choice.shuffled() as ArrayList<String>

        Glide.with(this)
                .load(communityQuestions[question_track].image_url)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_answer_question)
        tv_answer_question.text = communityQuestions[question_track].question

        repeat(list_buttons.size) {
            list_buttons[it].text = random_questions.removeAt(0)
        }

        buttonChoiceClick(list_buttons, communityQuestions, track)

        button_next_question.setOnClickListener {
            media_default.start()
            button_next_question.visibility = View.INVISIBLE
            repeat(list_buttons.size) {
                list_buttons[it].setBackgroundColor(resources.getColor(R.color.color_white))
                list_buttons[it].setTextColor(resources.getColor(R.color.color_grey))
                list_buttons[it].isClickable = true
            }
            loadQuestions(communityQuestions, ++question_track, view, list_buttons)
        }
    }

    private fun buttonChoiceClick(list_buttons: ArrayList<Button>, communityQuestions: ArrayList<CommunityQuestion>, question_track: Int) {
        val correct_response = communityQuestions[question_track].multiple_choice[0]
        repeat(list_buttons.size) { position ->
            list_buttons[position].setOnClickListener { view ->
                disableAllButtons(list_buttons)
                if (list_buttons[position].text == correct_response) {
                    alertCorrectResponse(view)
                } else alertWrongResponse(view, list_buttons, correct_response)
                button_next_question.visibility = View.VISIBLE
            }
        }
    }

    private fun disableAllButtons(list_buttons: ArrayList<Button>) {
        repeat(list_buttons.size) {
            list_buttons[it].isClickable = false
        }
    }

    private fun listButtons(): ArrayList<Button> {
        val list_buttons = ArrayList<Button>()
        list_buttons.add(button_choice_one)
        list_buttons.add(button_choice_two)
        list_buttons.add(button_choice_three)
        list_buttons.add(button_choice_four)
        return list_buttons
    }

    private fun navigateBackToDetail(view: View) {
        val action = AnswerQuestionDirections.actionAnswerQuestionFragmentToDetailFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun alertWrongResponse(view: View, list_buttons: ArrayList<Button>, correct_response: String) {
        val button = view as Button
        media_wrong.start()
        button.setBackgroundColor(resources.getColor(R.color.color_wrong))
        button.setTextColor(resources.getColor(R.color.color_white))
        repeat(list_buttons.size) { position ->
            if (list_buttons[position].text == correct_response) {
                list_buttons[position].setBackgroundColor(resources.getColor(R.color.color_correct))
                list_buttons[position].setTextColor(resources.getColor(R.color.color_white))
            }
        }
    }

    private fun alertCorrectResponse(view: View) {
        val button = view as Button
        media_correct.start()
        button.setBackgroundColor(resources.getColor(R.color.color_correct))
        button.setTextColor(resources.getColor(R.color.color_white))
    }

    override fun onDestroy() {
        super.onDestroy()
        media_default.release()
        media_correct.release()
        media_wrong.release()
    }
}
