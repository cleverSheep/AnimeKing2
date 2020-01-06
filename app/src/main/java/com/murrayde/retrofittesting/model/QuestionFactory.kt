@file:Suppress("FunctionName", "LocalVariableName")

package com.murrayde.retrofittesting.model

import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.retrofittesting.util.QuestionUtility

class QuestionFactory {
    val db = FirebaseFirestore.getInstance()

    companion object {
        private val current_anime = HashMap<String, Int>()
        private var id_count = 0

        fun BUILD(question: String, image_url: String, multiple_choice: ArrayList<String>, issue_count: Int, anime_title: String): Question {
            if (current_anime.containsKey(anime_title)) {
                id_count = current_anime[anime_title]!!
                id_count++
                current_anime[anime_title] = id_count
            } else current_anime[anime_title] = 0
            return Question(question, image_url, current_anime.getValue(anime_title), multiple_choice, issue_count, anime_title)
        }

        fun RETRIEVE(anime_title: String, db: FirebaseFirestore, status: StatusCallback): ArrayList<Question> {
            // Store each question id into hashmap to guarantee we ask unique questions
            val distinct_questions = ArrayList<Int>()
            val final_questions = ArrayList<Question>()

            db.collection("anime").document(anime_title).collection("questions").limit(QuestionUtility.LIMIT).get().addOnSuccessListener {
                it.forEach { snapshot ->
                    val question = snapshot.toObject(Question::class.java)
                    if (!distinct_questions.contains(question.question_id)) {
                        final_questions.add(question)
                    }
                }
                status.onStatusCallback(final_questions)
            }
            return final_questions
        }

        fun hasEnoughQuestions(anime_title: String): Boolean {
            if (current_anime[anime_title] == null) return false
            return current_anime[anime_title]!! >= 2
        }

        fun CLEAR() {
            current_anime.clear()
        }
    }

    interface StatusCallback {
        fun onStatusCallback(list: ArrayList<Question>)
    }
}