package com.murrayde.retrofittesting.model

data class Question(val question: String,
                    val anime: String,
                    val question_id: Int,
                    val answer: Int,
                    val multiple_choice: ArrayList<String>,
                    val has_image: Boolean)