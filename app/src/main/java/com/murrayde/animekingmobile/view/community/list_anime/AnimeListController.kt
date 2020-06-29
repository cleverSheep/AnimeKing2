@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingmobile.view.community.list_anime

import android.widget.Toast
import androidx.navigation.Navigation
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.epoxy.models.AnimeView_
import com.murrayde.animekingmobile.epoxy.models.HeaderItemViewModel_
import com.murrayde.animekingmobile.epoxy.views.CarouselNoSnapModel_
import com.murrayde.animekingmobile.model.ui.AnimeForYou
import com.murrayde.animekingmobile.network.community.api.AnimeData
import java.util.ArrayList

class AnimeListController : TypedEpoxyController<AnimeForYou>() {
    override fun buildModels(data: AnimeForYou) {
        createDataCarousel(data.allAnimeTitles)
        createDataCarousel(data.popularAnimeTitles)
        createDataCarousel(data.topRomanceTitles)
    }

    private fun createDataCarousel(itemPair: Pair<String, List<AnimeData>>) {
        val models = ArrayList<AnimeView_>()
        for (item in itemPair.second) {
            models.add(
                    AnimeView_()
                            .id("item:" + item.id)
                            .anime_title(item.attributes.canonicalTitle)
            )
        }
        HeaderItemViewModel_()
                .id("header")
                .title(itemPair.first)
                .addTo(this)
        CarouselNoSnapModel_()
                .id("carousel-1")
                .models(models)
                .addTo(this)
    }

}