package com.murrayde.animeking.view.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murrayde.animeking.network.community.api.AnimeComplete
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.network.community.AnimeApiEndpoint
import com.murrayde.animeking.network.NetworkState
import com.murrayde.animeking.util.PagingUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AnimeDataSource(private val animeApiEndpoint: AnimeApiEndpoint,
                      private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<String, AnimeData>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, AnimeData>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
                animeApiEndpoint.getAllPopularAnime(PagingUtil.PAGING_LIMIT, PagingUtil.PAGING_OFFSET)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                networkState.postValue(NetworkState.LOADED)

                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                callback.onResult(animeDataArrayList, null, t.nextLink)
                            }

                            override fun onError(e: Throwable) {
                                networkState.postValue(NetworkState.error("Error loading anime titles"))
                            }

                        })
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
                animeApiEndpoint.getAllPopularAnime(PagingUtil.PAGING_LIMIT, PagingUtil.PAGING_OFFSET())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                networkState.postValue(NetworkState.LOADED)

                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                callback.onResult(animeDataArrayList, t.nextLink)
                            }

                            override fun onError(e: Throwable) {
                                networkState.postValue(NetworkState.error("Error loading anime titles"))
                            }

                        })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
    }
}