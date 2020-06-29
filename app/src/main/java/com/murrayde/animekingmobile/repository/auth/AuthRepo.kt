package com.murrayde.animekingmobile.repository.auth

import com.murrayde.animekingmobile.network.community.AnimeApiClient
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import com.murrayde.animekingmobile.repository.Repository
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