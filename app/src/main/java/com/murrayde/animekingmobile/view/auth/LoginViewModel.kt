@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingmobile.view.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.di.community.AnimeApiComponent
import com.murrayde.animekingmobile.di.community.DaggerAnimeApiComponent
import com.murrayde.animekingmobile.network.community.AnimeApiEndpoint
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.network.community.api.AnimeData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel : ViewModel() {
    private var daggerAnimeApiComponent: AnimeApiComponent = DaggerAnimeApiComponent.builder()
            .build()
    private var animeApiEndpoint: AnimeApiEndpoint = daggerAnimeApiComponent.animeApiEndpoint
    private val compositeDisposable = CompositeDisposable()

    private val anime_images = MutableLiveData<ArrayList<AnimeData>>()
    private val manga_images = MutableLiveData<ArrayList<AnimeData>>()

    fun animeImages(): LiveData<ArrayList<AnimeData>> = anime_images
    fun mangaImages(): LiveData<ArrayList<AnimeData>> = manga_images

    fun fetchAnimeImages() {
        compositeDisposable.add(
                animeApiEndpoint.trendingAnime
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                Log.d("LoginViewModel", "Testing:${t.data[0].attributes.posterImage.original}")
                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                anime_images.postValue(animeDataArrayList)
                            }

                            override fun onError(e: Throwable) {
                                Timber.e("Error loading anime images")
                            }

                        })
        )
    }

    fun fetchMangaImages() {
        compositeDisposable.add(
                animeApiEndpoint.trendingManga
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
                                val animeDataArrayList: ArrayList<AnimeData> = ArrayList()
                                animeDataArrayList.addAll(t.data)
                                manga_images.postValue(animeDataArrayList)
                            }

                            override fun onError(e: Throwable) {
                                Timber.e("Error loading manga images")
                            }

                        })
        )
    }


}