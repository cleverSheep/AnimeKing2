@file:Suppress("LocalVariableName")

package com.murrayde.animeking.view.questions

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.murrayde.animeking.R
import com.murrayde.animeking.model.Question
import com.murrayde.animeking.view.list_anime.AnimeListDetailArgs
import kotlinx.android.synthetic.main.fragment_ask_question.*
import timber.log.Timber

class AskQuestion : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var media: MediaPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ask_question, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_ask_question)
        toolbar.setupWithNavController(findNavController())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)

        tv_ask_question.text = attributes.titles.en ?: attributes.canonicalTitle
        Glide.with(this)
                .load(attributes.posterImage.original)
                .placeholder(R.drawable.castle)
                .dontAnimate()
                .into(iv_ask_question)

        button_submit_question.setOnClickListener {
            media.start()
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
        val action = AskQuestionDirections.actionAskQuestionFragmentToDetailFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
        Timber.d("Media release, fragment destroyed")
    }

}
