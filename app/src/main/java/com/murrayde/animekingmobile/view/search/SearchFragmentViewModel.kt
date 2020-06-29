package com.murrayde.animekingmobile.view.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.repository.search.SearchRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchFragmentViewModel @ViewModelInject constructor(private val searchRepo: SearchRepo) : ViewModel() {

    private val list_anime = MutableLiveData<List<AnimeData>>()
    private val compositeDisposable = CompositeDisposable()

    fun search(anime_title: String) {
        compositeDisposable.add(
                searchRepo.getUserRequestedAnime(anime_title)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                list_anime.value = t.data
                            }

                            override fun onError(e: Throwable) {
                                Timber.d("No titles returned")
                            }

                        })
        )
    }

    fun getRequestedAnime(): LiveData<List<AnimeData>> = list_anime

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}