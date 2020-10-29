@file:Suppress("PrivatePropertyName", "MemberVisibilityCanBePrivate", "PropertyName")

package com.murrayde.animekingmobile.screen.game_over

import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.util.QuestionUtil
import timber.log.Timber

class GameOverViewModel @ViewModelInject() constructor() : ViewModel() {

    private var time_bonus = 0
    var total_time_bonus = 0
    private var total_correct = 0
    private var total_questions = 0
    private var total_score = 0

    private val questionReviewsSubmitted = MutableLiveData<Boolean>()
    private val db = FirebaseFirestore.getInstance()


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

    fun totalPoints() = time_bonus + total_score

    fun resetPoints(points: Int) {
        time_bonus = points
        total_correct = points
        total_time_bonus = points
    }

    fun totalCorrect() = total_correct

    fun positiveMessage(): Boolean = (total_correct.toFloat() / getTotalQuestions().toFloat()) > 0.5

    fun updateCurrentScore(currentScore: Int) {
        total_score = currentScore
    }

    /**Review question process*/

    fun submitQuestionReviews(listQuestions: List<CommunityQuestion>) {
        questionReviewsSubmitted.value = false
        val likedQuestionsRef = mutableListOf<DocumentReference>()
        val dislikedQuestionsRef = mutableListOf<DocumentReference>()
        val titlesFieldLocation = db.collection("anime")
                .document(LocaleListCompat.getDefault()[0].language)
                .collection("titles")

        listQuestions.forEach {
            if (it.isLiked) likedQuestionsRef.add(titlesFieldLocation.document(it.anime_title).collection("questions").document(it.question_id))
            else dislikedQuestionsRef.add(titlesFieldLocation.document(it.anime_title).collection("questions").document(it.question_id))

        }

        val likeFieldRef = HashMap<String, Any>()
        likeFieldRef["question_likes"] = FieldValue.increment(1)

        val dislikeFieldRef = HashMap<String, Any>()
        dislikeFieldRef["question_dislikes"] = FieldValue.increment(1)

        db.runBatch { batch ->
            likedQuestionsRef.forEach {
                batch.update(it, likeFieldRef)
            }
            dislikedQuestionsRef.forEach {
                batch.update(it, dislikeFieldRef)
            }
        }.addOnCompleteListener {
            questionReviewsSubmitted.value = true
            if (it.isSuccessful) {
                Timber.d("Question reviews submitted to firebase")
            } else Timber.d("Question review submission failed!")
        }
    }

    fun questionsAreReviewed(): LiveData<Boolean> = questionReviewsSubmitted

}