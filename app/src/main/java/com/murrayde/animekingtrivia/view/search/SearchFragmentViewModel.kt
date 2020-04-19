package com.murrayde.animekingtrivia.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingtrivia.di.community.DaggerAnimeApiComponent
import com.murrayde.animekingtrivia.network.community.api.AnimeComplete
import com.murrayde.animekingtrivia.network.community.api.AnimeData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchFragmentViewModel : ViewModel() {

    private val list_anime = MutableLiveData<List<AnimeData>>()

    val compositeDisposable = CompositeDisposable()
    val daggerAnimeApiComponent = DaggerAnimeApiComponent.builder().build()
    val animeApiEndpoint = daggerAnimeApiComponent.animeApiEndpoint

    fun search(anime_title: String) {
        compositeDisposable.add(
                animeApiEndpoint.getUserRequestedAnime(anime_title)
                        .subscribeOn(Schedulers.newThread())
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

    fun getRequestedAnime(): LiveData<List<AnimeData>> {
        return list_anime
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}