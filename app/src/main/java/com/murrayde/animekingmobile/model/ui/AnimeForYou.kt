package com.murrayde.animekingmobile.model.ui

import com.murrayde.animekingmobile.network.community.api.AnimeData

data class AnimeForYou(
        val allAnimeTitles: Pair<String, List<AnimeData>>,
        val popularAnimeTitles: Pair<String, List<AnimeData>>,
        val topRomanceTitles: Pair<String, List<AnimeData>>
)