@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingtrivia.view.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultsViewModel : ViewModel() {

    private var time_bonus = 0
    var total_time_bonus = 0
    private var total_correct_points = 0
    private var total_correct = 0
    private var total_questions = 0
    private var positive_message = true

    fun incrementTimeBonus(time: Int) {
        time_bonus += time
        total_time_bonus = time_bonus
    }

    fun updateTotalCorrect(correct: Int) {
        total_correct = correct
    }

    fun getTotalQuestions() = total_questions

    fun setTotalQuestions(number_questions: Int) {
        total_questions = number_questions
    }

    fun totalTimeBonus() = total_time_bonus

    fun totalPoints() = time_bonus + (total_correct * 2)

    fun resetPoints(points: Int) {
        time_bonus = points
        total_correct = points
        total_time_bonus = points
    }

    fun totalCorrect() = total_correct

    fun positiveMessage() = (total_correct.toFloat() / total_questions.toFloat()) > 0.5

}