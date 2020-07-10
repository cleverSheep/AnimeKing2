@file:Suppress("FunctionName", "LocalVariableName")

package com.murrayde.animekingmobile.model.community

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.util.QuestionUtil

class QuestionFactory {
    private val db = FirebaseFirestore.getInstance()

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

    interface QuestionCountCallback {
        fun onQuestionCountCallback(hasEnoughQuestions: Boolean)
    }
}