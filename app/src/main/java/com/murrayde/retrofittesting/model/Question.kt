@file:Suppress("FunctionName")

package com.murrayde.retrofittesting.model

data class Question(val question: String,
                    val image_url: String,
                    val question_id: Int,
                    val multiple_choice: ArrayList<String>,
                    val issue_count: Int,
                    val anime_title: String)

class QuestionBuilder {
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
    }
}