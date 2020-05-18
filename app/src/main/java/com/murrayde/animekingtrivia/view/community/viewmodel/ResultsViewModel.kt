@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingtrivia.view.community.viewmodel

import androidx.lifecycle.ViewModel

class ResultsViewModel : ViewModel() {

    private var time_bonus = 0
    private var total_time_bonus = 0
    private var total_correct_points = 0
    private var total_correct = 0

    fun incrementTimeBonus(time: Int) {
        total_time_bonus += time
        time_bonus = total_time_bonus
    }

    fun updateTotalCorrect(correct: Int) {
        total_correct = correct
    }

    fun totalTimeBonus() = time_bonus

    fun totalPoints() = total_time_bonus + (total_correct_points * 2)

    fun isPositiveMessage(total_questions: Int) = (total_correct / total_questions) > 0.5

}