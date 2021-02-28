package com.murrayde.animekingandroid.repository.search

import com.murrayde.animekingandroid.network.community.api_models.AnimeComplete
import com.murrayde.animekingandroid.network.community.clients.AnimeApiClient
import com.murrayde.animekingandroid.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {
    fun getUserRequestedAnime(title: String): Single<AnimeComplete> {
        return animeApiClient.getUserRequestedAnime(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}