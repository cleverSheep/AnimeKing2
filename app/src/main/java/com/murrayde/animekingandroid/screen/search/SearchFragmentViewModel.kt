@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingandroid.screen.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingandroid.model.ui.NetworkResponse
import com.murrayde.animekingandroid.network.community.api_models.AnimeComplete
import com.murrayde.animekingandroid.network.community.api_models.AnimeData
import com.murrayde.animekingandroid.repository.search.SearchRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SearchFragmentViewModel @ViewModelInject constructor(private val searchRepo: SearchRepo) : ViewModel() {

    private val list_anime = MutableLiveData<List<AnimeData>>()
    private val compositeDisposable = CompositeDisposable()
    private val response = MutableLiveData<NetworkResponse>()

    fun search(anime_title: String) {
        response.postValue(NetworkResponse.Loading)
        compositeDisposable.add(
                searchRepo.getUserRequestedAnime(anime_title)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                response.postValue(NetworkResponse.Success)
                                list_anime.value = t.data
                            }

                            override fun onError(e: Throwable) {
                                Timber.d("No titles returned")
                                response.postValue(NetworkResponse.Error)
                            }

                        })
        )
    }

    fun getRequestedAnime(): LiveData<List<AnimeData>> = list_anime
    var networkResponse: LiveData<NetworkResponse> = response

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}