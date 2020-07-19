package com.murrayde.animekingmobile.network.community.clients

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.util.removeForwardSlashes
import javax.inject.Inject

class FirebaseApiClient @Inject constructor() {
    private val firebaseDB = FirebaseFirestore.getInstance()

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String, receivedQuestions: ReceivedQuestions) {
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles")
                .document(removeForwardSlashes(anime_title)).collection("questions")

        val key = questionsRef.document().id
        val listOfQuestions = mutableListOf<CommunityQuestion>()
        var questionSize = QuestionUtil.QUESTION_LIMIT
        questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).limit(questionSize).get().addOnSuccessListener { documents ->
            documents.forEach {
                listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
            }
            receivedQuestions.onReceivedRandomQuestions(listOfQuestions)
        }
    }
}

interface ReceivedQuestions {
    fun onReceivedRandomQuestions(listOfQuestions: List<CommunityQuestion>)
}
