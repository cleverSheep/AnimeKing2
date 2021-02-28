package com.murrayde.animekingandroid.repository.community

import com.murrayde.animekingandroid.model.community.CommunityQuestion
import com.murrayde.animekingandroid.network.community.clients.FirebaseApiClient
import com.murrayde.animekingandroid.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnswerQuestionRepo @Inject constructor(private val firebaseApiClient: FirebaseApiClient) : Repository {

    fun getQuestions(anime_title: String): Single<List<CommunityQuestion>> {
        return firebaseApiClient.getQuestions(anime_title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}