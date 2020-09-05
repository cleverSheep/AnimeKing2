package com.murrayde.animekingmobile.screen.game

import android.util.Log
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

class AnswerQuestionViewModel @ViewModelInject constructor(private val answerQuestionRepo: AnswerQuestionRepo) : ViewModel() {

    private var listQuestions = MutableLiveData<List<CommunityQuestion>>()
    private val compositeDisposable = CompositeDisposable()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val listOfQuestions = mutableListOf<CommunityQuestion>()
    private var lock = 0

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String) {
        lock = 0
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles")
                .document(removeForwardSlashes(anime_title)).collection("questions")

        val key = questionsRef.document().id

        questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener { documents ->
            if (documents.size() > 0) {
                documents.forEach { listOfQuestions.add(it.toObject(CommunityQuestion::class.java)) }
                checkQuestionSize()
            } else {
                questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), " ").limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener {
                    documents.forEach { listOfQuestions.add(it.toObject(CommunityQuestion::class.java)) }
                    checkQuestionSize()
                }
            }
            if (listOfQuestions.size < QuestionUtil.QUESTION_LIMIT) {
                Log.d(AnswerQuestionViewModel::class.qualifiedName, " Add more questions!")
                questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).limit(QuestionUtil.QUESTION_LIMIT - listOfQuestions.size).get().addOnSuccessListener { documents ->
                    if (documents.size() > 0) {
                        documents.forEach {
                            Log.d(AnswerQuestionViewModel::class.qualifiedName, " Ok, so I'm going to add this question: ${it.toObject(CommunityQuestion::class.java).question}")
                            listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
                            checkQuestionSize()
                        }
                    } else {
                        questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), " ").limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener {
                            documents.forEach {
                                Log.d(AnswerQuestionViewModel::class.qualifiedName, " Ok, so I'm going to add this question: ${it.toObject(CommunityQuestion::class.java).question}")
                                listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
                                checkQuestionSize()
                            }
                        }
                        questionsRef.whereLessThanOrEqualTo(FieldPath.documentId(), " ").limit(QuestionUtil.QUESTION_LIMIT).get().addOnSuccessListener {
                            documents.forEach {
                                Log.d(AnswerQuestionViewModel::class.qualifiedName, " Ok, so I'm going to add this question: ${it.toObject(CommunityQuestion::class.java).question}")
                                listOfQuestions.add(it.toObject(CommunityQuestion::class.java))
                                checkQuestionSize()
                            }
                        }
                    }
                }
            }
        }
    }


    fun getListOfQuestions(): LiveData<List<CommunityQuestion>> = listQuestions

    private fun checkQuestionSize() {
        if (lock >= 2) return
        if (listOfQuestions.size.toLong() == QuestionUtil.QUESTION_LIMIT) {
            Log.d(AnswerQuestionViewModel::class.qualifiedName, " Total number of questions: ${listOfQuestions.size}")
            listQuestions.value = listOfQuestions
            lock += 1
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}