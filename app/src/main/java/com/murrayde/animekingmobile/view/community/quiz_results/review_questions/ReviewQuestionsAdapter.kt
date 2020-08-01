@file:Suppress("PropertyName")

package com.murrayde.animekingmobile.view.community.quiz_results.review_questions

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.model.community.CommunityQuestion


class ReviewQuestionsAdapter(val context: Context, private val list_questions: Array<CommunityQuestion>) : RecyclerView.Adapter<ReviewQuestionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_questions_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list_questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Initialize icon based on whether the response was correct
        if (list_questions[position].user_correct_response) {
            holder.review_status_icon.setImageResource(R.drawable.ic_results_correct_check)
        } else holder.review_status_icon.setImageResource(R.drawable.ic_results_wrong_icon)

        holder.review_question.text = list_questions[position].question
        holder.review_correct_answer.text = String.format(context.getString(R.string.review_correct_choice, list_questions[position].correctResponse))

        holder.review_like_button.setOnClickListener {
            list_questions[position].likeQuestion()
            it!!.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            it.background = it.context.resources.getDrawable(R.drawable.button_like_selector)
            holder.review_dislike_button.background = it.context.resources.getDrawable(R.drawable.ic_thumb_down)

        }
        holder.review_dislike_button.setOnClickListener {
            list_questions[position].dislikeQuestion()
            it!!.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            it.background = it.context.resources.getDrawable(R.drawable.button_dislike_selector)
            holder.review_like_button.background = it.context.resources.getDrawable(R.drawable.ic_thumb_up)
        }


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var review_status_icon: ImageView = view.findViewById(R.id.review_status_icon)
        var review_question: TextView = view.findViewById(R.id.review_question)
        var review_correct_answer: TextView = view.findViewById(R.id.review_correct_answer)

        var review_like_button: ToggleButton = view.findViewById(R.id.review_thumb_up)
        var review_dislike_button: ToggleButton = view.findViewById(R.id.review_thumb_down)
    }

}