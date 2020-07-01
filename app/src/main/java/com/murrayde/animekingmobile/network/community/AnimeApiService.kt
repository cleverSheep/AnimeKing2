package com.murrayde.animekingmobile.network.community

import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {

    /** Anime for you*/
    @GET("anime?filter[categories]=comedy,romance&sort=popularityRank&filter[ageRating]=PG&page[limit]=20")
    fun goofyButLovableTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=psychological&sort=popularityRank&page[limit]=20")
    fun takeAPotatoChip(): Single<AnimeComplete>

    @GET("anime?filter[categories]=magic,action&sort=popularityRank&page[limit]=20")
    fun alchemyWizardsFairies(): Single<AnimeComplete>

    @GET("anime?filter[categories]=Shounen,action&sort=popularityRank&page[limit]=20")
    fun putYourSkillsInAction(): Single<AnimeComplete>

    @GET("anime?filter[categories]=romance&sort=popularityRank&page[limit]=20")
    fun buddingRomanceTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=adventure,action&sort=popularityRank&filter[ageRating]=G,PG&page[limit]=20")
    fun letsGoOnAnAdventure(): Single<AnimeComplete>

    @GET("anime?filter[categories]=Horror,dark&sort=popularityRank&page[limit]=20")
    fun darkAnimeTrivia(): Single<AnimeComplete>

    @GET("anime?filter[categories]=mecha,action&sort=popularityRank&page[limit]=20")
    fun everythingMecha(): Single<AnimeComplete>

    @GET("anime?filter[year]=1998&sort=popularityRank&page[limit]=20")
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