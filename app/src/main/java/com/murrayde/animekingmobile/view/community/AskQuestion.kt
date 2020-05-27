@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view.community

import android.content.DialogInterface
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.formatQuestion
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.util.removeForwardSlashes
import com.murrayde.animekingmobile.view.community.list_anime.AnimeListDetailArgs
import kotlinx.android.synthetic.main.fragment_ask_question.*


class AskQuestion : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var media: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences

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
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        tv_ask_question.text = attributes.titles.en ?: attributes.canonicalTitle
        Glide.with(this)
                .load(attributes.coverImage?.original ?: attributes.posterImage.original)
                .placeholder(R.drawable.crown_list_screen)
                .dontAnimate()
                .into(iv_ask_question)

        button_submit_question.setOnClickListener {
            if (editText_enter_question.text.toString().length < 12) {
                showDialogForBadQuestion(view)
            } else if (
                    TextUtils.isEmpty(editText_wrong_one.text) ||
                    TextUtils.isEmpty(editText_wrong_two.text) ||
                    TextUtils.isEmpty(editText_wrong_three.text) ||
                    TextUtils.isEmpty(editText_correct_choice.text)) {
                showDialogForBadAnswerChoice(view)
            } else {
                it.isEnabled = false
                media.start()
                progressBar_ask_question.visibility = View.VISIBLE
                // NOTE: For some reason, I have to initialize 'question' inside of the click listener...
                val multiple_choice = arrayListOf(editText_correct_choice.text.toString(),
                        editText_wrong_one.text.toString(),
                        editText_wrong_two.text.toString(),
                        editText_wrong_three.text.toString())
                val stringBuilder = StringBuilder()
                val checkQuestionFormat = stringBuilder.formatQuestion(editText_enter_question.text.toString())
                val question = CommunityQuestion(checkQuestionFormat,
                        attributes.posterImage.original,
                        "",
                        multiple_choice,
                        0,
                        tv_ask_question.text.toString(),
                        false)
                addQuestionToDatabase(question, view)
            }
        }
    }

    private fun addQuestionToDatabase(communityQuestion: CommunityQuestion, view: View) {
        //addQuestionToDatabase("anime")
        //NOTE: Question_Count reflects the database value not the HashMap<..> value
        //NOTE: This callback supposedly causes a memory leak
        val question_id = db.collection("anime")
                .document(LocaleListCompat.getDefault()[0].language)
                .collection("titles")
                .document(removeForwardSlashes(tv_ask_question.text.toString()))
                .collection("questions")
                .document().id
        communityQuestion.question_id = question_id
        db.collection("anime")
                .document(LocaleListCompat.getDefault()[0].language)
                .collection("titles")
                .document(removeForwardSlashes(tv_ask_question.text.toString()))
                .collection("questions")
                .document(question_id)
                .set(communityQuestion)
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
                .document(LocaleListCompat.getDefault()[0].language)
                .collection("titles")
                .document(removeForwardSlashes(tv_ask_question.text.toString()))
        questionCountRef.set(data, SetOptions.merge())
    }

    private fun navigateBackToDetailScreen(view: View) {
        val action = AskQuestionDirections.actionAskQuestionFragmentToDetailFragment(args.animeAttributes)
        Navigation.findNavController(view).navigate(action)
    }

    private fun showDialogForBadQuestion(view: View) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view.findViewById<ViewGroup>(R.id.main_view_content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(activity!!).inflate(R.layout.empty_question_fix, viewGroup, false)

        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(activity!!)


        val button = dialogView.findViewById<Button>(R.id.accept_request_button)

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView).setPositiveButton("") { _: DialogInterface, _: Int -> }
        builder.setNegativeButton("") { _: DialogInterface, _: Int -> }

        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        button.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showDialogForBadAnswerChoice(view: View) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        val viewGroup = view.findViewById<ViewGroup>(R.id.main_view_content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView = LayoutInflater.from(activity!!).inflate(R.layout.empty_answer_fix, viewGroup, false)

        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(activity!!)


        val button = dialogView.findViewById<Button>(R.id.accept_request_button)

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView).setPositiveButton("") { _: DialogInterface, _: Int -> }
        builder.setNegativeButton("") { _: DialogInterface, _: Int -> }

        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        button.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }

}
