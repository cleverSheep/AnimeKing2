package com.murrayde.animekingmobile.view.community.questions.answer_question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.model.community.CommunityQuestion
import com.murrayde.animekingmobile.repository.community.AnswerQuestionRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AnswerQuestionViewModel @ViewModelInject constructor(private val answerQuestionRepo: AnswerQuestionRepo) : ViewModel() {

    private var listQuestions = MutableLiveData<List<CommunityQuestion>>()
    private val compositeDisposable = CompositeDisposable()

    fun getQuestions(anime_title: String) {
        compositeDisposable.add(
                answerQuestionRepo.getQuestions(anime_title)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<CommunityQuestion>>() {
                            override fun onSuccess(t: List<CommunityQuestion>) {
                                listQuestions.value = t
                            }

                            override fun onError(e: Throwable) {
                                Timber.d("No questions returned")
                            }

                        })
        )
    }

    fun getListOfQuestions(): LiveData<List<CommunityQuestion>> = listQuestions

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}