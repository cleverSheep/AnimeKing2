package com.murrayde.animekingmobile.view.community.list_anime

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.repository.community.MainRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AnimeListViewModel @ViewModelInject constructor(private val mainRepo: MainRepo) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val animeForYou = MutableLiveData<AnimeForYou>()
    private val loading = MutableLiveData<Boolean>()

    init {
        fetchAnimeForYou()
    }

    private fun fetchAnimeForYou() {
        loading.postValue(true)
        disposable.add(
                mainRepo.getAnimeForYou().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeForYou>() {
                            override fun onSuccess(forYou: AnimeForYou) {
                                loading.postValue(false)
                                animeForYou.value = forYou
                            }

                            override fun onError(e: Throwable) {
                                loading.postValue(false)
                                Timber.e("Error loading titles")

                            }

                        })
        )
    }

    fun getAnimeForYou(): LiveData<AnimeForYou> = animeForYou

    val networkLoading: LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}