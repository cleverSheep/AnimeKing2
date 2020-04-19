@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingtrivia.view.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingtrivia.di.random.DaggerTriviaApiComponent
import com.murrayde.animekingtrivia.model.random.RandomQuestion
import com.murrayde.animekingtrivia.model.random.Result
import com.murrayde.animekingtrivia.network.random.TriviaDbApiEndpoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RandomQuestionsViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable
    private var triviaApiComponent: TriviaDbApiEndpoint

    private val question_result: MutableLiveData<List<Result>> by lazy {
        MutableLiveData<List<Result>>().also {
            loadQuestionSet()
        }
    }

    init {
        val daggerTriviaApiComponent = DaggerTriviaApiComponent.builder().build()
        triviaApiComponent = daggerTriviaApiComponent.triviaApiEndpoint
        compositeDisposable = CompositeDisposable()
    }


    private fun loadQuestionSet() {
        compositeDisposable.add(
                triviaApiComponent.getTriviaApiResult()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<RandomQuestion>() {
                            override fun onSuccess(t: RandomQuestion) {
                                val question_list: ArrayList<Result> = ArrayList()
                                question_list.addAll(t.results)
                                question_result.value = question_list
                            }

                            override fun onError(e: Throwable) {
                            }

                        })
        )
    }

    fun getQuestionSet(): LiveData<List<Result>> {
        return question_result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}