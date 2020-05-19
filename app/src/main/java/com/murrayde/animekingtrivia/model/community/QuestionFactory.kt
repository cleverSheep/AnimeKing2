@file:Suppress("FunctionName", "LocalVariableName")

package com.murrayde.animekingtrivia.model.community

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingtrivia.util.QuestionUtil

class QuestionFactory {
    private val db = FirebaseFirestore.getInstance()

    companion object {
        fun RETRIEVE(anime_title: String, db: FirebaseFirestore, context: Context, status: StatusCallback): ArrayList<CommunityQuestion> {
            // Store each question id into hashmap to guarantee we ask unique questions
            val distinct_questions = ArrayList<String>()
            val final_questions = ArrayList<CommunityQuestion>()
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

            db.collection("anime").document(sharedPreferences.getString("language", "en")!!).collection("titles").document(anime_title).collection("questions").limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener { query_snapshot ->
                val collection_questions = randomlySelectQuestions(query_snapshot.documents)
                query_snapshot.forEach { snapshot ->
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

        private fun randomlySelectQuestions(documents: List<DocumentSnapshot>): List<DocumentSnapshot> {
            val random_questions: List<DocumentSnapshot> = emptyList()
            while (random_questions.size < QuestionUtil.QUESTION_LIMIT) {
                val indices_list = ArrayList<Int>(documents.size)
            }
            return random_questions
        }
    }

    fun hasEnoughQuestions(anime_title: String, questionCountCallback: QuestionCountCallback, context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val doc_ref = db.collection("anime").document(sharedPreferences.getString("language", "en")!!).collection("titles").document(anime_title)
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