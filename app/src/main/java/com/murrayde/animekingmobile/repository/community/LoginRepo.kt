package com.murrayde.animekingmobile.repository.community

import com.murrayde.animekingmobile.network.community.AnimeApiClient
import com.murrayde.animekingmobile.network.community.AnimeApiService
import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepo @Inject constructor(private val animeApiClient: AnimeApiClient) {

    fun getTrendingAnimeTitles(): Single<AnimeComplete> {
        return animeApiClient.getTrendingAnimeTitles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrendingMangaTitles(): Single<AnimeComplete> {
        return animeApiClient.getTrendingMangaTitles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}