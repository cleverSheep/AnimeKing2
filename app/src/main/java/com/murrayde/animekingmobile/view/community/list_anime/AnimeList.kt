@file:Suppress("PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_anime


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.hideView
import com.murrayde.animekingmobile.extensions.showView
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.view.community.data_source.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber
import javax.inject.Inject

class AnimeList : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeListController = AnimeListController()
        rv_anime.setController(animeListController)

        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        viewModel.getAnimeForYou().observe(viewLifecycleOwner, Observer { animeForYou ->
            animeListController.setData(animeForYou)
        })

    }

}
