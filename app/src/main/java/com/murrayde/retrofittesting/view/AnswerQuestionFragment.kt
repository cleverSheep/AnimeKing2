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

class AnswerQuestionFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
                tv_answer_title.text = args.animeAttributes.titles.en
                        ?: args.animeAttributes.canonicalTitle
                loadQuestions(questions, 0, view)
            }
        })
    }

    // TODO: WRITE TEST FOR loadQuestions(...)
    private fun loadQuestions(questions: ArrayList<Question>, track: Int, view: View) {
        // NOTE: Make sure to explicitly return or else the remaining lines of code will be executed
        if (track >= questions.size) {
            navigateBackToDetail(view)
            return
        }
        var question_track = track
        val random_questions: ArrayList<String> = questions[question_track].multiple_choice.shuffled() as ArrayList<String>

        Glide.with(this)
                .load(questions[question_track].image_url)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_answer_question)
        tv_answer_question.text = questions[question_track].question

        button_choice_one.text = random_questions.removeAt(0)
        button_choice_two.text = random_questions.removeAt(0)
        button_choice_three.text = random_questions.removeAt(0)
        button_choice_four.text = random_questions.removeAt(0)

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
            loadQuestions(questions, ++question_track, view)
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
}
