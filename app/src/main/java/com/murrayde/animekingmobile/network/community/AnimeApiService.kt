package com.murrayde.animekingmobile.network.community

import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    @GET("anime?sort=popularityRank")
    fun allPopularAnime(): Single<AnimeComplete>

    /** Anime for you*/
    @GET("anime?filter[categories]=comedy,romance&sort=popularityRank&filter[ageRating]=PG")
    fun goofyButLovableTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=psychological&sort=popularityRank")
    fun takeAPotatoChip(): Single<AnimeComplete>

    @GET("anime?filter[categories]=magic,action&sort=popularityRank")
    fun alchemyWizardsFairies(): Single<AnimeComplete>

    @GET("anime?filter[categories]=Shounen,action&sort=popularityRank")
    fun putYourSkillsInAction(): Single<AnimeComplete>

    @GET("anime?filter[categories]=romance&sort=popularityRank")
    fun buddingRomanceTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=adventure,action&sort=popularityRank&filter[ageRating]=G,PG")
    fun letsGoOnAnAdventure(): Single<AnimeComplete>

    @GET("anime?filter[categories]=Horror,dark&sort=popularityRank")
    fun darkAnimeTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=mecha,action&sort=popularityRank")
    fun everythingMecha(): Single<AnimeComplete>

    @GET("anime?filter[year]=1998&sort=popularityRank")
    fun classicalAnimeTrivia(): Single<AnimeComplete>

    /**Used for the login screen*/
    @GET("trending/anime")
    fun trendingAnime(): Single<AnimeComplete>

    @GET("trending/manga")
    fun trendingManga(): Single<AnimeComplete>

    /**Used for the search engine*/
    @GET("anime?page[limit]=20")
    fun getUserRequestedAnime(@Query("filter[text]") title: String?): Single<AnimeComplete>
}