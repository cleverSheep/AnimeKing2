@file:Suppress("LocalVariableName")

package com.murrayde.retrofittesting.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.Question
import com.murrayde.retrofittesting.model.QuestionFactory
import kotlinx.android.synthetic.main.fragment_answer_question.*
import timber.log.Timber
import kotlin.random.Random

/**
 * A simple [Fragment] subclass.
 */
class AnswerQuestionFragment : Fragment() {

    companion object {
        val LOG_TAG = AnswerQuestionFragment::class.simpleName
    }

    private val args: DetailFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes

        var questions: ArrayList<Question>
        QuestionFactory.RETRIEVE(attributes.titles.en
                ?: attributes.canonicalTitle, db, object : QuestionFactory.StatusCallback {
            override fun onStatusCallback(list: ArrayList<Question>) {
                questions = list
                loadQuestions(questions, 0, view)
            }
        })
    }
    // TODO: WRITE TEST FOR loadQuestions(...)
    private fun loadQuestions(questions: ArrayList<Question>, track: Int, view: View) {
        var question_track = track
        val random_question = ArrayList<Int>()
        if (question_track >= questions.size) navigateBackToDetail(view)

        Glide.with(this)
                .load(questions[question_track].image_url)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_answer_question)
        Timber.d(LOG_TAG, "Image: ${questions[question_track].image_url}")
        tv_answer_question.text = questions[question_track].question

        val choice_one = Random.nextInt(0, 4)
        random_question.add(choice_one)
        button_choice_one.text = questions[question_track].multiple_choice[choice_one]

        var choice_two = Random.nextInt(0, 4)
        if (random_question.contains(choice_two)) {
            choice_two = newChoice(choice_two, random_question)
        } else random_question.add(choice_two)
        button_choice_two.text = questions[question_track].multiple_choice[choice_two]

        var choice_three = Random.nextInt(0, 4)
        if (random_question.contains(choice_three)) {
            choice_three = newChoice(choice_three, random_question)
        } else random_question.add(choice_three)
        button_choice_three.text = questions[question_track].multiple_choice[choice_three]

        var choice_four = Random.nextInt(0, 4)
        if (random_question.contains(choice_four)) {
            choice_four = newChoice(choice_four, random_question)
        } else random_question.add(choice_four)
        button_choice_four.text = questions[question_track].multiple_choice[choice_four]

        button_choice_one.setOnClickListener {
            if (questions[question_track].multiple_choice[0] == button_choice_one.text) alertCorrectResponse()
            else alertWrongResponse()
            button_next_question.visibility = View.VISIBLE
        }

        button_choice_two.setOnClickListener {
            if (questions[question_track].multiple_choice[0] == button_choice_two.text) alertCorrectResponse()
            else alertWrongResponse()
            button_next_question.visibility = View.VISIBLE
        }

        button_choice_three.setOnClickListener {
            if (questions[question_track].multiple_choice[0] == button_choice_three.text) alertCorrectResponse()
            else alertWrongResponse()
            button_next_question.visibility = View.VISIBLE
        }

        button_choice_four.setOnClickListener {
            if (questions[question_track].multiple_choice[0] == button_choice_four.text) alertCorrectResponse()
            else alertWrongResponse()
            button_next_question.visibility = View.VISIBLE
        }

        button_next_question.setOnClickListener {
            button_next_question.visibility = View.INVISIBLE
            question_track += 1
            loadQuestions(questions, question_track, view)
        }

    }

    private fun navigateBackToDetail(view: View) {
        val action = AnswerQuestionFragmentDirections.actionAnswerQuestionFragmentToDetailFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun alertWrongResponse() {
        Toast.makeText(activity, "Sorry, incorrect response!", Toast.LENGTH_SHORT).show()
    }

    private fun alertCorrectResponse() {
        Toast.makeText(activity, "Congratulations, correct response!", Toast.LENGTH_SHORT).show()
    }
    // TODO: WRITE TEST FOR newChoice(...)
    private fun newChoice(choice: Int, randomQuestion: ArrayList<Int>): Int {
        var update = choice
        if (randomQuestion.size >= 4) return 0
        while (randomQuestion.contains(update)) {
            update = Random.nextInt(0, 4)
        }
        return update
    }

}
