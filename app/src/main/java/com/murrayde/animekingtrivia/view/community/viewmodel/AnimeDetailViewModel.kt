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

    private val member_count = MutableLiveData<Long>()
    private val question_count = MutableLiveData<Long>()
    private val db = FirebaseFirestore.getInstance()

    fun getMemberCount(anime_title: String): LiveData<Long> {
        GlobalScope.launch {
            val doc_ref = db.collection("anime").document(anime_title)
            doc_ref.get().addOnSuccessListener { doc_snapshot ->
                if (doc_snapshot.getLong("member_count") == null) {
                    Timber.d("$anime_title member_count: NULL")
                } else {
                    member_count.value = doc_snapshot.getLong("member_count")
                }
            }
        }
        return member_count
    }

    fun incrementMemberCount(anime_title: String) {
        GlobalScope.launch {
            val data = HashMap<String, Any>()
            data["member_count"] = FieldValue.increment(1)

            val memberCountRef = db.collection("anime")
                    .document(anime_title)
            memberCountRef.set(data, SetOptions.merge())
        }
    }

    fun getQuestionCount(anime_title: String): LiveData<Long> {
        GlobalScope.launch {
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
        }
        return question_count
    }

}