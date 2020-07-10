@file:Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate", "PropertyName")

package com.murrayde.animekingmobile.view.community.quiz_results

import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.util.QuestionUtil
import timber.log.Timber

class ResultsViewModel : ViewModel() {

    private var time_bonus = 0
    var total_time_bonus = 0
    private var total_correct = 0
    private var total_questions = 0

    fun incrementTimeBonus(time: Int) {
        time_bonus += time
        total_time_bonus = time_bonus
    }

    fun updateTotalCorrect(correct: Int) {
        total_correct = correct
    }

    fun getTotalQuestions() = if (total_questions > QuestionUtil.QUESTION_LIMIT) 10 else total_questions

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

    fun positiveMessage(): Boolean {
        Timber.d("Total score: ${total_correct.toFloat() / total_questions.toFloat()}")
        return (total_correct.toFloat() / getTotalQuestions().toFloat()) > 0.5
    }

}