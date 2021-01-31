@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.screen.main.detail

import androidx.core.os.LocaleListCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingmobile.model.community.PlayHistory
import com.murrayde.animekingmobile.repository.community.AnimeDetailRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class AnimeDetailViewModel @ViewModelInject constructor(private val animeDetailRepo: AnimeDetailRepo) : ViewModel() {

    private val question_count = MutableLiveData<Long>()
    private val db = FirebaseFirestore.getInstance()
    private val disposable = CompositeDisposable()
    private val highScore = MutableLiveData<Int>()

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

    // TODO: Update high score after game ends
    fun setHighScore(animeTitle: String, userId: String, highScore: Int) {
        val playHistory = PlayHistory(highScore)
        GlobalScope.launch {
            db.collection("users").document("$userId").collection("play_history").document("$animeTitle")
                    .set(playHistory)
        }
    }

    fun getHighScore(animeTitle: String, userId: String) {
        disposable.add(
                animeDetailRepo.retrieveHighScore(animeTitle, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<PlayHistory>() {
                            override fun onSuccess(playHistory: PlayHistory) {
                                highScore.postValue(playHistory.highScore)
                                Timber.d("High score: ${playHistory.highScore}")
                            }

                            override fun onError(e: Throwable) {
                                highScore.postValue(0)
                                setHighScore(animeTitle, userId, 0)
                            }

                        }))
    }

    var animeHighScore: LiveData<Int> = highScore

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}