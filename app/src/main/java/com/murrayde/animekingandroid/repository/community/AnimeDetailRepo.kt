package com.murrayde.animekingandroid.repository.community

import com.murrayde.animekingandroid.model.community.PlayHistory
import com.murrayde.animekingandroid.network.community.clients.FirebaseApiClient
import com.murrayde.animekingandroid.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnimeDetailRepo @Inject constructor(private val firebaseApiClient: FirebaseApiClient) : Repository {

    fun retrieveHighScore(animeTitle: String, userId: String): Single<PlayHistory> {
        return firebaseApiClient.getHighScore(animeTitle, userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }
}