package com.murrayde.animekingmobile.model.ui

import com.murrayde.animekingmobile.network.community.api.AnimeData

data class AnimeForYou(
        val goofyButLovableTrivia: Pair<String, List<AnimeData>>,
        val takeAPotatoChip: Pair<String, List<AnimeData>>,
        val alchemyWizardsFairies: Pair<String, List<AnimeData>>,
        val putYourSkillsInAction: Pair<String, List<AnimeData>>,
        val buddingRomanceTrivia: Pair<String, List<AnimeData>>,
        val letsGoOnAnAdventure: Pair<String, List<AnimeData>>,
        val darkAnimeTrivia: Pair<String, List<AnimeData>>,
        val everythingMecha: Pair<String, List<AnimeData>>,
        val classicalAnimeTrivia: Pair<String, List<AnimeData>>
)