package com.murrayde.animekingmobile.screen.main.master

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.model.ui.NetworkResponse
import com.murrayde.animekingmobile.repository.community.AnimeListRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AnimeListViewModel @ViewModelInject constructor(private val animeListRepo: AnimeListRepo) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val animeForYou = MutableLiveData<AnimeForYou>()
    private val response = MutableLiveData<NetworkResponse>()

    init {
        fetchAnimeForYou()
    }

    private fun fetchAnimeForYou() {
        response.postValue(NetworkResponse.Loading)
        disposable.add(
                animeListRepo.getAnimeForYou().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeForYou>() {
                            override fun onSuccess(forYou: AnimeForYou) {
                                response.postValue(NetworkResponse.Success)
                                animeForYou.value = forYou
                            }

                            override fun onError(e: Throwable) {
                                response.postValue(NetworkResponse.Error)
                                Timber.e("Error loading titles")

                            }

                        })
        )
    }

    fun getAnimeForYou(): LiveData<AnimeForYou> = animeForYou

    val networkResponse: LiveData<NetworkResponse> = response

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}