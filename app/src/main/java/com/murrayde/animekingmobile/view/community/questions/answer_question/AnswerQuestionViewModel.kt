package com.murrayde.animekingmobile.view.community.questions.answer_question

import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.repository.community.AnswerQuestionRepo
import com.murrayde.animekingmobile.util.QuestionUtil
import com.murrayde.animekingmobile.util.removeForwardSlashes
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class AnswerQuestionViewModel @ViewModelInject constructor(private val answerQuestionRepo: AnswerQuestionRepo) : ViewModel() {

    private var listQuestions = MutableLiveData<List<CommunityQuestion>>()
    private val compositeDisposable = CompositeDisposable()
    private val firebaseDB = FirebaseFirestore.getInstance()

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String) {
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles")
                .document(removeForwardSlashes(anime_title)).collection("questions")

        val listOfQuestions = mutableListOf<CommunityQuestion>()
        val questionSize = QuestionUtil.QUESTION_LIMIT

        for (i in 1..7) {
            val key = questionsRef.document().id
            questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).limit(1).get().addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    documents.forEach {
                        Timber.d("Adding: ${it.toObject(CommunityQuestion::class.java).question}")
                        listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
                    }
                } else {
                    questionsRef.whereLessThanOrEqualTo(FieldPath.documentId(), key).limit(1).get().addOnSuccessListener { documents ->
                        documents.forEach {
                            Timber.d("Adding: ${it.toObject(CommunityQuestion::class.java).question}")
                            listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
                        }
                    }
                }
                listQuestions.value = listOfQuestions
            }
            Timber.d("Questions size: ${listOfQuestions.size}")
        }
    }

    fun getListOfQuestions(): LiveData<List<CommunityQuestion>> = listQuestions


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}