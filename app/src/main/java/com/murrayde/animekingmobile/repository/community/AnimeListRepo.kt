package com.murrayde.animekingmobile.repository.community

import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.clients.AnimeApiClient
import com.murrayde.animekingmobile.repository.Repository
import com.murrayde.animekingmobile.util.performAnimeTitleFiltering
import io.reactivex.Single
import io.reactivex.functions.Function9
import javax.inject.Inject

class AnimeListRepo @Inject constructor(private val animeApiClient: AnimeApiClient) : Repository {

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
                            Pair("Goofy but lovable trivia", performAnimeTitleFiltering(goofyButLovableTrivia.data)),
                            Pair("I'll take a potato chip...", performAnimeTitleFiltering(takeAPotatoChip.data)),
                            Pair("Alchemy, wizards and fairies", performAnimeTitleFiltering(alchemyWizardsFairies.data)),
                            Pair("Put your skills in action", performAnimeTitleFiltering(putYourSkillsInAction.data)),
                            Pair("Budding romance trivia", performAnimeTitleFiltering(buddingRomanceTrivia.data)),
                            Pair("Let's go on an adventure!", performAnimeTitleFiltering(letsGoOnAnAdventure.data)),
                            Pair("Dark anime trivia", performAnimeTitleFiltering(darkAnimeTrivia.data)),
                            Pair("Everything mecha", performAnimeTitleFiltering(everythingMecha.data)),
                            Pair("Classical anime trivia", performAnimeTitleFiltering(classicalAnimeTrivia.data))
                    ))
                }
        )
    }

    private fun buildAnimeForYou(animeForYou: AnimeForYou): AnimeForYou =
            AnimeForYou(
                    animeForYou.goofyButLovableTrivia, animeForYou.takeAPotatoChip, animeForYou.alchemyWizardsFairies, animeForYou.putYourSkillsInAction,
                    animeForYou.buddingRomanceTrivia, animeForYou.letsGoOnAnAdventure, animeForYou.darkAnimeTrivia, animeForYou.everythingMecha, animeForYou.classicalAnimeTrivia
            )
}