package com.murrayde.animekingmobile.network.community

import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime?sort=popularityRank")
    fun allPopularAnime(): Single<AnimeComplete>

    @GET("trending/anime")
    fun trendingAnime(): Single<AnimeComplete>

    @GET("trending/manga")
    fun trendingManga(): Single<AnimeComplete>
}