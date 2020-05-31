package com.murrayde.animekingmobile.view.community.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint
import com.murrayde.animekingmobile.util.PagingUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AnimeDataSource(private val animeApiEndpoint: AnimeApiEndpoint,
                      private val compositeDisposable: CompositeDisposable,
                      private val networkUIStateLoading: MutableLiveData<Boolean>) : PageKeyedDataSource<String, AnimeData>() {

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, AnimeData>) {
        networkUIStateLoading.postValue(true)
        compositeDisposable.add(
                animeApiEndpoint.getAllPopularAnime(PagingUtil.PAGING_LIMIT, PagingUtil.PAGING_OFFSET)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                networkUIStateLoading.postValue(false)

                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                callback.onResult(animeDataArrayList, null, t.nextLink)
                            }

                            override fun onError(e: Throwable) {
                                networkUIStateLoading.postValue(false)
                            }

                        })
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
        compositeDisposable.add(
                animeApiEndpoint.getAllPopularAnime(PagingUtil.PAGING_LIMIT, PagingUtil.PAGING_OFFSET())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {

                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                callback.onResult(animeDataArrayList, t.nextLink)
                            }

                            override fun onError(e: Throwable) {
                            }

                        })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
    }
}