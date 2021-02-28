package com.murrayde.animekingandroid.repository.community

import com.murrayde.animekingandroid.R
import com.murrayde.animekingandroid.application.BaseApplication
import com.murrayde.animekingandroid.model.ui.AnimeForYou
import com.murrayde.animekingandroid.network.community.clients.AnimeApiClient
import com.murrayde.animekingandroid.repository.Repository
import com.murrayde.animekingandroid.util.performAnimeTitleFiltering
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
                            Pair(BaseApplication.getResourses().getString(R.string.goofy_lovable), performAnimeTitleFiltering(goofyButLovableTrivia.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.potato_chip), performAnimeTitleFiltering(takeAPotatoChip.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.alchemy_wizards), performAnimeTitleFiltering(alchemyWizardsFairies.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.skills_in_action), performAnimeTitleFiltering(putYourSkillsInAction.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.budding_romance), performAnimeTitleFiltering(buddingRomanceTrivia.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.go_on_adventure), performAnimeTitleFiltering(letsGoOnAnAdventure.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.dark_anime), performAnimeTitleFiltering(darkAnimeTrivia.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.everything_mecha), performAnimeTitleFiltering(everythingMecha.data)),
                            Pair(BaseApplication.getResourses().getString(R.string.classical_anime), performAnimeTitleFiltering(classicalAnimeTrivia.data))
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