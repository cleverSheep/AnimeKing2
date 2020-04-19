package com.murrayde.animekingtrivia.view.community.results_screen.review_questions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.model.community.CommunityQuestion

class ReviewQuestionsAdapter(val context: Context, val list_questions: Array<CommunityQuestion>) : RecyclerView.Adapter<ReviewQuestionsAdapter.ViewHolder>() {

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
        holder.review_correct_answer.text = "Correct: ${list_questions[position].correctResponse}"

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var review_status_icon: ImageView = view.findViewById(R.id.review_status_icon)
        var review_question: TextView = view.findViewById(R.id.review_question)
        var review_correct_answer: TextView = view.findViewById(R.id.review_correct_answer)
    }
}