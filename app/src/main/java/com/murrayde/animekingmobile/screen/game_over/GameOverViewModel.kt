@file:Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate", "PropertyName")

package com.murrayde.animekingmobile.screen.game_over

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.PlayHistory
import com.murrayde.animekingmobile.model.player.PlayerExperience
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.util.removeForwardSlashes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class GameOverViewModel @ViewModelInject() constructor() : ViewModel() {

    private var total_correct = 0
    private var high_score = 0
    private var time_bonus = 0

    // Display total questions in anime detail
    private var total_questions = 0

    private lateinit var playerExperience: PlayerExperience
    private val required_exp = MutableLiveData<Int>()

    private val playerXPLiveData = MutableLiveData<Int>()
    private val playerPreviousXPLiveData = MutableLiveData<Int>()
    private val playerXPToLevelUp = MutableLiveData<Int>()
    private val playerCurrentLevel = MutableLiveData<Int>()

    private val gameTotalCorrectLD = MutableLiveData<Int>()
    private val gameHighScoreBonusLD = MutableLiveData<Int>()
    private val gameQuizScoreLD = MutableLiveData<Int>()
    private val gameTimeBonusLD = MutableLiveData<Int>()

    private val db = FirebaseFirestore.getInstance()


    fun incrementTimeBonus(currentTime: Int) {
        if (currentTime >= (QuestionUtil.QUESTION_TIMER / 1000) - 5) {
            time_bonus += QuestionUtil.TIME_BONUS
        }
    }

    fun updateTotalCorrect() {
        total_correct += 1
        gameTotalCorrectLD.value = total_correct
        Timber.d("total correct: $total_correct")
    }

    fun getTotalCorrect(): Int = total_correct

    fun getTotalXP(): Int {
        var xp = 10 * total_correct + time_bonus
        if (total_correct > high_score) {
            val highScoreBonus = 12 * (total_correct + time_bonus)
            xp += highScoreBonus
            gameHighScoreBonusLD.value = highScoreBonus
        }
        gameTimeBonusLD.value = time_bonus
        gameQuizScoreLD.value = xp
        return xp
    }

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

    fun getRequiredExperience(userId: String) {
        var totalExperience = getTotalXP()
        GlobalScope.launch {
            val docRef = db.collection("users").document(userId)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    playerExperience = documentSnapshot.toObject(PlayerExperience::class.java)!!
                    playerPreviousXPLiveData.postValue(playerExperience.total_exp)
                    playerExperience.total_exp += totalExperience
                    while (playerExperience.total_exp >= playerExperience.req_exp) {
                        playerExperience.level += 1
                        playerCurrentLevel.postValue(playerExperience.level.toInt())
                        playerExperience.total_exp -= playerExperience.req_exp
                        playerExperience.req_exp += 20
                    }
                    playerCurrentLevel.postValue(playerExperience.level.toInt())
                    required_exp.postValue(playerExperience.req_exp)
                    playerXPLiveData.postValue(playerExperience.total_exp)
                    playerXPToLevelUp.postValue(playerExperience.req_exp - playerExperience.total_exp)
                    updatePlayerStats(userId)
                    return@addOnSuccessListener
                }
            }
        }
    }

    var getGameTotalCorrectLD = gameTotalCorrectLD
    var getHighScoreBonusLD = gameHighScoreBonusLD
    var getGameQuizScoreLD = gameQuizScoreLD
    var getGameBonusTimeLD = gameTimeBonusLD

    fun getUpdatedPlayerXPLiveData(): LiveData<Int> = playerXPLiveData
    fun getPreviousPlayerXPLiveData(): LiveData<Int> = playerPreviousXPLiveData
    fun getPlayerRequiredXP(): LiveData<Int> = required_exp
    fun getPlayerXPToLevelUp(): LiveData<Int> = playerXPToLevelUp
    fun getPlayerCurrentLevel(): LiveData<Int> = playerCurrentLevel

    fun updatePlayerStats(userId: String) {
        GlobalScope.launch {
            db.collection("users").document(userId).set(playerExperience)
        }
    }

    fun setTotalQuestions(number_questions: Int) {
        total_questions = number_questions
    }

    fun resetPoints(points: Int) {
        time_bonus = points
        total_correct = points
    }

}