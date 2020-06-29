package com.murrayde.animekingmobile.view.community.data_source

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

class MainActivityViewModel @ViewModelInject constructor(private val mainRepo: MainRepo) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val animeForYou = MutableLiveData<AnimeForYou>()
    private val networkDoneLoading = MutableLiveData<Boolean>()

    init {
        fetchAnimeForYou()
    }

    private fun fetchAnimeForYou() {
        networkDoneLoading.value = false
        disposable.add(
                mainRepo.getAnimeForYou().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeForYou>() {
                            override fun onSuccess(t: AnimeForYou) {
                                networkDoneLoading.value = true
                                animeForYou.value = t
                            }

                            override fun onError(e: Throwable) {
                                networkDoneLoading.value = true
                                Timber.e("Error loading titles")
                            }

                        })
        )
    }

    fun getAnimeForYou(): LiveData<AnimeForYou> = animeForYou

    fun networkDoneLoading(): LiveData<Boolean> = networkDoneLoading

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}