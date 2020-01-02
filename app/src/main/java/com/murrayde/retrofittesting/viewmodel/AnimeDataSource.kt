package com.murrayde.retrofittesting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murrayde.retrofittesting.model.AnimeComplete
import com.murrayde.retrofittesting.model.AnimeData
import com.murrayde.retrofittesting.network.AnimeApiEndpoint
import com.murrayde.retrofittesting.network.NetworkState
import com.murrayde.retrofittesting.util.PAGING
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
                animeApiEndpoint.getAnimeComplete(PAGING.PAGING_LIMIT, PAGING.PAGING_OFFSET)
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
                            }

                        })
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
                animeApiEndpoint.getAnimeComplete(PAGING.PAGING_LIMIT, PAGING.PAGING_OFFSET())
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
                                // TODO : PROVIDE RETRY OPERATION
                            }

                        })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
    }
}