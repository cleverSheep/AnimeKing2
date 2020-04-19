@file:Suppress("PrivatePropertyName")

package com.murrayde.animekingtrivia.view.community.list_anime

import android.widget.Toast
import androidx.navigation.Navigation
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.network.community.api.AnimeData
import com.murrayde.animekingtrivia.view.community.list_anime.item.*

class AnimeListAdapter : PagedListEpoxyController<AnimeData>() {

    override fun buildItemModel(currentPosition: Int, item: AnimeData?): EpoxyModel<*> {

        item?.let {
            // Anime item model
            if (currentPosition == 0) {
                return HeaderView_()
                        .id("Header")
                        .title(R.string.random_anime)
                        .onClickListener { view ->
                            val directions = AnimeListDirections.actionHomeToAnswerRandomQuestions()
                            Navigation.findNavController(view).navigate(directions)
                        }
            }
            return AnimeView_()
                    .id("anime_id:${currentPosition}")
                    .anime_title(item.attributes.titles.en ?: item.attributes.canonicalTitle)
                    .image_url(item.attributes.posterImage.original
                            ?: item.attributes.coverImage.original)
                    .onClickListener { view ->
                        val action = AnimeListDirections.actionListFragmentToDetailFragment(item.attributes)
                        Navigation.findNavController(view).navigate(action)
                    }
                    .onLongClickListener {view ->
                        Toast.makeText(view.context, "${item.attributes?.titles?.en ?: item.attributes?.canonicalTitle}", Toast.LENGTH_SHORT).show()
                        true
                    }
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