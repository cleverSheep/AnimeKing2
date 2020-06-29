package com.murrayde.animekingmobile.repository.community

import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.AnimeApiClient
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class MainRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {

    fun getAnimeForYou(): Single<AnimeForYou> {
        return Single.zip(
                animeApiClient.getPopularAnimeTitles(),
                animeApiClient.getTrendingAnimeTitles(),
                animeApiClient.getTrendingMangaTitles(),
                Function3 { animeTitles, trendingAnime, trendingManga ->
                    buildAnimeForYou(
                            AnimeForYou(
                                    Pair("All Anime Titles", animeTitles.data),
                                    Pair("Popular Anime Titles", trendingAnime.data),
                                    Pair("All Trending Manga", trendingManga.data)
                            )
                    )
                })
    }

    private fun buildAnimeForYou(animeForYou: AnimeForYou): AnimeForYou =
            AnimeForYou(
                    animeForYou.allAnimeTitles,
                    animeForYou.popularAnimeTitles,
                    animeForYou.topRomanceTitles
            )
}