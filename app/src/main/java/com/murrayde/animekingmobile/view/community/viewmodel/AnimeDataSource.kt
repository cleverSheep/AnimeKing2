package com.murrayde.animekingmobile.view.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.murrayde.animekingmobile.extensions.lastCharacterIsDigit
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint
import com.murrayde.animekingmobile.util.PagingUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

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
                                callback.onResult(performAnimeTitleFiltering(animeDataArrayList), null, t.nextLink)
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
                                callback.onResult(performAnimeTitleFiltering(animeDataArrayList), t.nextLink)
                            }

                            override fun onError(e: Throwable) {
                            }

                        })
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, AnimeData>) {
    }

    private fun performAnimeTitleFiltering(animeDataArrayList: ArrayList<AnimeData>): List<AnimeData> {
        return animeDataArrayList.asSequence().filter {
            if (it.attributes.synopsis == null) return@filter false else !it.attributes.synopsis.contains("season", true)
        }.filter {
            if (it.attributes.slug == null) return@filter false else !it.attributes.slug.contains("season", true)
        }.filter {
            if (it.attributes.slug == null) return@filter false else !it.attributes.slug.contains("ii", true)
        }.filter {
            if (it.attributes.slug == null) return@filter false else !it.attributes.slug.lastCharacterIsDigit()
        }.filter {
            if (it.attributes.slug == null) return@filter false else it.attributes.slug.isNotEmpty()
        }.filter {
            if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.contains("season", true)
        }.filter {
            if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.contains("ii", true)
        }.filter {
            if (it.attributes.titles.en == null) return@filter false else !it.attributes.titles.en.lastCharacterIsDigit()
        }.filter {
            if (it.attributes.titles.en == null) return@filter false else it.attributes.titles.en.isNotEmpty()
        }.filter {
            if (it.attributes.titles.enJp == null) return@filter false else !it.attributes.titles.enJp.contains("season", true)
        }.filter {
            if (it.attributes.titles.jaJp == null) return@filter false else !it.attributes.titles.jaJp.contains("season", true)
        }.filter {
            if (it.attributes.canonicalTitle == null) return@filter false else !it.attributes.canonicalTitle.contains("season", true)
        }.toList() as ArrayList<AnimeData>
    }
}