@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.view.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.murrayde.animekingtrivia.util.QuestionUtil
import kotlinx.android.synthetic.main.fragment_ask_question.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeDetailViewModel : ViewModel() {
    private val question_count = MutableLiveData<Long>()
    private val db = FirebaseFirestore.getInstance()

    fun getQuestionCount(anime_title: String): LiveData<Long> {
        GlobalScope.launch {
            val doc_ref = db.collection("anime").document(anime_title)
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