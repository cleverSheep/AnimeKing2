package com.murrayde.animekingmobile.network.community

import com.murrayde.animekingmobile.network.community.api.AnimeComplete
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnimeApiClient @Inject constructor(
        private val animeApiService: AnimeApiService
) {

    /** Anime for you*/
    fun goofyButLovableTrivia(): Single<AnimeComplete> {
        return animeApiService.goofyButLovableTrivia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun takeAPotatoChip(): Single<AnimeComplete> {
        return animeApiService.takeAPotatoChip()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    fun alchemyWizardsFairies(): Single<AnimeComplete> {
        return animeApiService.alchemyWizardsFairies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun putYourSkillsInAction(): Single<AnimeComplete> {
        return animeApiService.putYourSkillsInAction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun buddingRomanceTrivia(): Single<AnimeComplete> {
        return animeApiService.buddingRomanceTrivia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun letsGoOnAnAdventure(): Single<AnimeComplete> {
        return animeApiService.letsGoOnAnAdventure()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun darkAnimeTrivia(): Single<AnimeComplete> {
        return animeApiService.darkAnimeTrivia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun everythingMecha(): Single<AnimeComplete> {
        return animeApiService.everythingMecha()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun classicalAnimeTrivia(): Single<AnimeComplete> {
        return animeApiService.classicalAnimeTrivia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    /**Used for the login screen*/
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

    /**Used for the search engine*/
    fun getUserRequestedAnime(title: String): Single<AnimeComplete> {
        return animeApiService.getUserRequestedAnime(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}