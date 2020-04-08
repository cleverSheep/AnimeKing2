@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.view.community.list_anime.item.*
import timber.log.Timber

class AnimeListAdapter : PagedListEpoxyController<AnimeData>() {

    override fun buildItemModel(currentPosition: Int, item: AnimeData?): EpoxyModel<*> {
        item?.let {
            // Anime item model
            if (currentPosition == 0) {
                return HeaderView_()
                        .id("Header")
                        .title(R.string.random_anime)
                        .onClickListener(View.OnClickListener {
                            Timber.d("Header clicked")
                        })
            }
            return AnimeView_()
                    .id("anime_id:${currentPosition}")
                    .anime_title(item.attributes.titles.en ?: "Unknown Title")
                    .image_url(item.attributes.posterImage.original
                            ?: item.attributes.coverImage.original)
                    .onClickListener(View.OnClickListener {
                        Timber.d("Item clicked")
                    })
        } ?: return LoadingModel_().id("loading")
    }

/*    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(
                models.plus(
                        HeaderView_()
                                .id("Header")
                                .title(R.string.random_anime)
                                .onClickListener(View.OnClickListener {
                                    Timber.d("Header clicked")
                                })
                )
        )
    }*/

}