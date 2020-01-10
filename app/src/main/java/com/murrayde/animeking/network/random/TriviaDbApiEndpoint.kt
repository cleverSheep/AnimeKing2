package com.murrayde.animeking.network.random

import com.murrayde.animeking.model.random.RandomQuestion
import io.reactivex.Single
import retrofit2.http.GET

interface TriviaDbApiEndpoint {
    @GET("api.php?amount=10&category=31&type=multiple&encode=url3986")
    fun getTriviaApiResult(): Single<RandomQuestion>
}