@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.list_anime

import com.airbnb.epoxy.TypedEpoxyController
import com.murrayde.animekingmobile.epoxy.models.AnimeView_
import com.murrayde.animekingmobile.epoxy.models.HeaderItemViewModel_
import com.murrayde.animekingmobile.epoxy.views.CarouselNoSnapModel_
import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.api.AnimeData
import java.util.*

class AnimeListController : TypedEpoxyController<AnimeForYou>() {
    override fun buildModels(data: AnimeForYou) {
        createDataCarousel(data.goofyButLovableTrivia)
        createDataCarousel(data.takeAPotatoChip)
        createDataCarousel(data.alchemyWizardsFairies)
        createDataCarousel(data.putYourSkillsInAction)
        createDataCarousel(data.buddingRomanceTrivia)
        createDataCarousel(data.letsGoOnAnAdventure)
        createDataCarousel(data.darkAnimeTrivia)
        createDataCarousel(data.everythingMecha)
        createDataCarousel(data.classicalAnimeTrivia)
    }

    private fun createDataCarousel(itemPair: Pair<String, List<AnimeData>>) {
        val models = ArrayList<AnimeView_>()
        for (item in itemPair.second) {
            models.add(
                    AnimeView_()
                            .id("item:" + item.id)
                            .anime_title(item.attributes.canonicalTitle)
                            .image_url(item.attributes.posterImage.original)
            )
        }
        HeaderItemViewModel_()
                .id("header:${itemPair.first}")
                .title(itemPair.first)
                .addTo(this)
        CarouselNoSnapModel_()
                .id("carousel:${itemPair.first}")
                .models(models)
                .addTo(this)
    }

}