package com.murrayde.animekingmobile.network.community.clients

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.model.community.PlayHistory
import com.murrayde.animekingmobile.util.removeForwardSlashes
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiClient @Inject constructor() {
    private val firebaseDB = FirebaseFirestore.getInstance()

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String): Single<List<CommunityQuestion>> {
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles")
                .document(removeForwardSlashes(anime_title)).collection("questions")

        val key = questionsRef.document().id

/*
        val listOfQuestions = mutableListOf<CommunityQuestion>()
        var questionSize = 0L
        questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).limit(questionSize).get().addOnSuccessListener { documents ->
            documents.forEach {
                listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
            }
        }
        return Single.just(listOfQuestions)
*/
        return questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).getSingle()
    }

    /** High score for an anime title*/
    fun getHighScore(anime_title: String, user_id: String): Single<PlayHistory> {
        val highScoreRef = firebaseDB.collection("users").document("$user_id").collection("play_history").document("$anime_title")
        return highScoreRef.getSingle()
    }
}
