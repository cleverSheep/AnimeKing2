@file:Suppress("LocalVariableName")

package com.murrayde.retrofittesting.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.Question
import kotlinx.android.synthetic.main.fragment_ask_question.*

class AskQuestionFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ask_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes

        tv_ask_question.text = attributes.titles.en ?: attributes.canonicalTitle
        Glide.with(this)
                .load(attributes.posterImage.original)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_ask_question)

        button_submit_question.setOnClickListener {
            progressBar_ask_question.visibility = View.VISIBLE
            // NOTE: For some reason, I have to initialize 'question' inside of the click listener...
            val multiple_choice = arrayListOf(editText_correct_choice.text.toString(),
                    editText_wrong_one.text.toString(),
                    editText_wrong_two.text.toString(),
                    editText_wrong_three.text.toString())
            val question = Question(editText_enter_question.text.toString(),
                    attributes.posterImage.original,
                    "",
                    multiple_choice,
                    0,
                    tv_ask_question.text.toString())
            addQuestionToDatabase(question, view)
        }
    }

    private fun addQuestionToDatabase(question: Question, view: View) {
        //addQuestionToDatabase("anime")
        //NOTE: Question_Count reflects the database value not the HashMap<..> value
        //NOTE: This callback supposedly causes a memory leak
        val question_id = db.collection("anime")
                .document(tv_ask_question.text.toString())
                .collection("questions")
                .document().id
        question.question_id = question_id
        db.collection("anime")
                .document(tv_ask_question.text.toString())
                .collection("questions")
                .document(question_id)
                .set(question)
                .addOnSuccessListener {
                    progressBar_ask_question.visibility = View.INVISIBLE
                    navigateBackToDetailScreen(view)
                }
                .addOnFailureListener {
                    progressBar_ask_question.visibility = View.INVISIBLE
                }

        val data = HashMap<String, Any>()
        data["question_count"] = FieldValue.increment(1)

        val questionCountRef = db.collection("anime")
                .document(tv_ask_question.text.toString())
        questionCountRef.set(data, SetOptions.merge())
    }

    private fun navigateBackToDetailScreen(view: View) {
        val action = AskQuestionFragmentDirections.actionAskQuestionFragmentToDetailFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

}
