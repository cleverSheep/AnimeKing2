@file:Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate", "PropertyName")

package com.murrayde.animekingmobile.screen.game_over

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.PlayHistory
import com.murrayde.animekingmobile.util.removeForwardSlashes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameOverViewModel @ViewModelInject() constructor() : ViewModel() {

    private var time_bonus = 0
    var total_time_bonus = 0
    private var total_correct = 0
    private var total_questions = 0
    private var total_score = 0
    private var high_score = 0
    private val db = FirebaseFirestore.getInstance()


    fun incrementTimeBonus(time: Int) {
        time_bonus += time
        total_time_bonus = time_bonus
    }

    fun updateTotalCorrect() {
        total_correct += 1
    }

    fun getTotalCorrect(): Int = total_correct

    fun updateHighScore(highScore: Int) {
        high_score = highScore
    }

    fun updateBackendHighScore(userId: String, animeTitle: String, highScore: Int) {
        val playHistory = PlayHistory(highScore)
        GlobalScope.launch {
            db.collection("users").document(userId).collection("play_history").document(removeForwardSlashes(animeTitle))
                    .set(playHistory)
        }
    }

    fun getHighScore(): Int = high_score

    fun setTotalQuestions(number_questions: Int) {
        total_questions = number_questions
    }

    fun resetPoints(points: Int) {
        time_bonus = points
        total_correct = points
        total_time_bonus = points
    }

    fun updateCurrentScore(currentScore: Int) {
        total_score = currentScore
    }

}