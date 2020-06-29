package com.murrayde.animekingmobile.network.community

import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnimeApiClient @Inject constructor(
        private val animeApiService: AnimeApiService
) {
    fun getPopularAnimeTitles(): Single<AnimeComplete> {
        return animeApiService.allPopularAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrendingAnimeTitles(): Single<AnimeComplete> {
        return animeApiService.trendingAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrendingMangaTitles(): Single<AnimeComplete> {
        return animeApiService.trendingManga()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserRequestedAnime(title: String): Single<AnimeComplete> {
        return animeApiService.getUserRequestedAnime(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}