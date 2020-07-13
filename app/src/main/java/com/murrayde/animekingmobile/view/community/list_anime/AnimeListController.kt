@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.list_anime

import androidx.navigation.Navigation
import com.airbnb.epoxy.TypedEpoxyController
import com.murrayde.animekingmobile.epoxy.models.AnimeView_
import com.murrayde.animekingmobile.epoxy.models.HeaderItemViewModel_
import com.murrayde.animekingmobile.epoxy.models.HeaderView_
import com.murrayde.animekingmobile.epoxy.views.CarouselNoSnapModel_
import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.api_models.AnimeData
import com.murrayde.animekingmobile.util.barrierText
import java.util.*

class AnimeListController : TypedEpoxyController<AnimeForYou>() {
    override fun buildModels(data: AnimeForYou) {
        HeaderView_()
                .id("List-header")
                .addTo(this)
        createDataCarousel(data.putYourSkillsInAction)
        createDataCarousel(data.letsGoOnAnAdventure)
        createDataCarousel(data.alchemyWizardsFairies)
        createDataCarousel(data.goofyButLovableTrivia)
        createDataCarousel(data.buddingRomanceTrivia)
        createDataCarousel(data.takeAPotatoChip)
        createDataCarousel(data.everythingMecha)
        createDataCarousel(data.darkAnimeTrivia)
        createDataCarousel(data.classicalAnimeTrivia)
    }

    private fun createDataCarousel(itemPair: Pair<String, List<AnimeData>>) {
        val models = ArrayList<AnimeView_>()
        for (item in itemPair.second) {
            val animeTitle = item.attributes.titles.en
                    ?: item.attributes.canonicalTitle
            val showType = item.attributes.ageRating ?: "--"
            models.add(
                    AnimeView_()
                            .id("item:" + item.id)
                            .anime_title(animeTitle)
                            .image_url(item.attributes.posterImage.original)
                            .num_of_questions(showType) // For testing
                            .barrier_text(barrierText())
                            .onClickListener { view ->
                                val action = AnimeListDirections.actionListFragmentToDetailFragment(item.attributes)
                                Navigation.findNavController(view).navigate(action)
                            }
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