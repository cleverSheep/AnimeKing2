package com.murrayde.animekingmobile.model.random

import androidx.annotation.Keep

@Keep
data class Result(
        val category: String,
        val correct_answer: String,
        val difficulty: String,
        val incorrect_answers: List<String>,
        val question: String,
        val type: String
)