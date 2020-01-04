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
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.retrofittesting.R
import com.murrayde.retrofittesting.model.QuestionBuilder
import kotlinx.android.synthetic.main.fragment_ask_question.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class AskQuestionFragment : Fragment() {

    companion object {
        val LOG_TAG = AskQuestionFragment::class.simpleName
    }

    private val args: DetailFragmentArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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

        val multiple_choice = arrayListOf(editText_correct_choice.text.toString(),
                editText_wrong_one.text.toString(),
                editText_wrong_two.text.toString(),
                editText_wrong_three.text.toString())
        val question = QuestionBuilder.BUILD(editText_enter_question.text.toString(),
                attributes.posterImage.original,
                multiple_choice,
                0,
                tv_ask_question.text.toString())

        button_submit_question.setOnClickListener {
            progressBar_ask_question.visibility = View.VISIBLE
            db.collection("anime")
                    .document(tv_ask_question.text.toString())
                    .collection("questions")
                    .add(question)
                    .addOnSuccessListener {
                        progressBar_ask_question.visibility = View.INVISIBLE
                        Timber.d("Question: %s", editText_enter_question.text.toString())
                        navigateBackToHomeScreen(view)
                    }
                    .addOnFailureListener {
                        progressBar_ask_question.visibility = View.INVISIBLE
                        Timber.w(it, "Error adding document")
                    }
        }
    }

    private fun navigateBackToHomeScreen(view: View) {
        val action = AskQuestionFragmentDirections.actionAskQuestionFragmentToListFragment()
        Navigation.findNavController(view).navigate(action)
    }

}
