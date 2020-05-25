@file:Suppress("FunctionName", "LocalVariableName")

package com.murrayde.animekingmobile.model.community

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.util.QuestionUtil

class QuestionFactory {
    private val db = FirebaseFirestore.getInstance()

    companion object {
        fun RETRIEVE(anime_title: String, db: FirebaseFirestore, status: StatusCallback): ArrayList<CommunityQuestion> {
            // Store each question id into hashmap to guarantee we ask unique questions
            val distinct_questions = ArrayList<String>()
            val final_questions = ArrayList<CommunityQuestion>()

            db.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles").document(anime_title).collection("questions").limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener {
                it.forEach { snapshot ->
                    val question = snapshot.toObject(CommunityQuestion::class.java)
                    if (!distinct_questions.contains(question.question_id)) {
                        final_questions.add(question)
                    }
                }
                // NOTE: When waiting on data from an async use callback to notify client when the data was received
                // Don't execute code in a synchronous behavior
                status.onStatusCallback(final_questions)
            }
            return final_questions
        }
    }

    fun hasEnoughQuestions(anime_title: String, questionCountCallback: QuestionCountCallback) {
        val doc_ref = db.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles").document(anime_title)
        doc_ref.get().addOnSuccessListener { doc_snapshot ->
            if (doc_snapshot.getLong("question_count") == null) {
                questionCountCallback.onQuestionCountCallback(false)
            } else {
                questionCountCallback.onQuestionCountCallback((doc_snapshot.getLong("question_count"))!! >= QuestionUtil.QUESTION_COUNT_MIN)
            }
        }
    }

    interface StatusCallback {
        fun onStatusCallback(list: ArrayList<CommunityQuestion>)
    }

    interface QuestionCountCallback {
        fun onQuestionCountCallback(hasEnoughQuestions: Boolean)
    }
}