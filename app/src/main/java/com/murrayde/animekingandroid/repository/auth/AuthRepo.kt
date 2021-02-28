package com.murrayde.animekingandroid.repository.auth

import com.murrayde.animekingandroid.network.community.clients.AnimeApiClient
import com.murrayde.animekingandroid.network.community.api_models.AnimeComplete
import com.murrayde.animekingandroid.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {

    fun getTrendingAnimeTitles(): Single<AnimeComplete> {
        return animeApiClient.getTrendingAnimeTitles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrendingMangaTitles(): Single<AnimeComplete> {
        return animeApiClient.getTrendingMangaTitles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}