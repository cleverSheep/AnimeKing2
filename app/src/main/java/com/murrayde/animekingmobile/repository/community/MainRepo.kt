package com.murrayde.animekingmobile.repository.community

import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.AnimeApiClient
import com.murrayde.animekingmobile.repository.Repository
import io.reactivex.Single
import io.reactivex.functions.Function9
import javax.inject.Inject

class MainRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {

    fun getAnimeForYou(): Single<AnimeForYou> {
        return Single.zip(
                animeApiClient.goofyButLovableTrivia(),
                animeApiClient.takeAPotatoChip(),
                animeApiClient.alchemyWizardsFairies(),
                animeApiClient.putYourSkillsInAction(),
                animeApiClient.buddingRomanceTrivia(),
                animeApiClient.letsGoOnAnAdventure(),
                animeApiClient.darkAnimeTrivia(),
                animeApiClient.everythingMecha(),
                animeApiClient.classicalAnimeTrivia(),
                Function9 { goofyButLovableTrivia, takeAPotatoChip, alchemyWizardsFairies, putYourSkillsInAction, buddingRomanceTrivia, letsGoOnAnAdventure,
                            darkAnimeTrivia, everythingMecha, classicalAnimeTrivia ->
                    buildAnimeForYou(AnimeForYou(
                            Pair("Goofy but loveable trivia", goofyButLovableTrivia.data),
                            Pair("I'll take a potato chip...", takeAPotatoChip.data),
                            Pair("Alchemy, wizards and fairies!", alchemyWizardsFairies.data),
                            Pair("Put your skills in action!", putYourSkillsInAction.data),
                            Pair("Budding romance trivia", buddingRomanceTrivia.data),
                            Pair("Let's go on an adventure!", letsGoOnAnAdventure.data),
                            Pair("Dark anime trivia", darkAnimeTrivia.data),
                            Pair("Everything mecha", everythingMecha.data),
                            Pair("Classical anime trivia", classicalAnimeTrivia.data)
                    ))
                }
        )
    }

    private fun buildAnimeForYou(animeForYou: AnimeForYou): AnimeForYou =
            AnimeForYou(
                    animeForYou.goofyButLovableTrivia, animeForYou.takeAPotatoChip, animeForYou.alchemyWizardsFairies, animeForYou.putYourSkillsInAction, animeForYou.buddingRomanceTrivia,
                    animeForYou.letsGoOnAnAdventure, animeForYou.darkAnimeTrivia, animeForYou.everythingMecha, animeForYou.classicalAnimeTrivia
            )
}