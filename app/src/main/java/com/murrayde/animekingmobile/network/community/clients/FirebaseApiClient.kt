package com.murrayde.animekingmobile.network.community.clients

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiClient @Inject constructor() {
    private val firebaseDB = FirebaseFirestore.getInstance()

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String): Single<List<CommunityQuestion>> {
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles").document(anime_title).collection("questions")
        return questionsRef.getSingle()
    }
}