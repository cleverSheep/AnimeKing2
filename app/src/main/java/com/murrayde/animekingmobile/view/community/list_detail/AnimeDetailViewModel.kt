@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_detail

import androidx.core.os.LocaleListCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeDetailViewModel : ViewModel() {
    private val question_count = MutableLiveData<Long>()
    private val db = FirebaseFirestore.getInstance()

    fun getQuestionCount(anime_title: String): LiveData<Long> {
        GlobalScope.launch {
            Timber.d("Current language: ${LocaleListCompat.getDefault()[0].language}")
            val doc_ref = db.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles").document(anime_title)
            doc_ref.get().addOnSuccessListener { doc_snapshot ->
                if (doc_snapshot.getLong("question_count") == null) {
                    Timber.d("$anime_title question_count: NULL")
                } else {
                    question_count.value = doc_snapshot.getLong("question_count")
                }
            }
        }
        return question_count
    }

}