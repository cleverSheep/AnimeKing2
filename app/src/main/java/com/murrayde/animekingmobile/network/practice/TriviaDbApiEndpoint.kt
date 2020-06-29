package com.murrayde.animekingmobile.network.practice

import com.murrayde.animekingmobile.model.practice.RandomQuestion
import io.reactivex.Single
import retrofit2.http.GET

interface TriviaDbApiEndpoint {
    @GET("api.php?amount=10&category=31&type=multiple&command=request")
    fun getTriviaApiResult(): Single<RandomQuestion>
}