@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingmobile.view.auth

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.repository.community.LoginRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(private val loginRepo: LoginRepo) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val anime_images = MutableLiveData<ArrayList<AnimeData>>()
    private val manga_images = MutableLiveData<ArrayList<AnimeData>>()

    fun animeImages(): LiveData<ArrayList<AnimeData>> = anime_images
    fun mangaImages(): LiveData<ArrayList<AnimeData>> = manga_images

    fun fetchAnimeImages() {
        compositeDisposable.add(
                loginRepo.getTrendingAnimeTitles()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<AnimeComplete>() {
                            override fun onSuccess(t: AnimeComplete) {
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
                loginRepo.getTrendingMangaTitles()
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