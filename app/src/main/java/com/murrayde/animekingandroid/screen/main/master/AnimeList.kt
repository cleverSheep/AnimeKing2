@file:Suppress("PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingandroid.screen.main.master


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.murrayde.animekingandroid.R
import com.murrayde.animekingandroid.extensions.hideView
import com.murrayde.animekingandroid.extensions.showView
import com.murrayde.animekingandroid.model.ui.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*

@AndroidEntryPoint
class AnimeList : Fragment() {

    private val viewModel: AnimeListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeListController = AnimeListController()
        rv_anime.setController(animeListController)

        super.onCreate(savedInstanceState)
        viewModel.getAnimeForYou().observe(viewLifecycleOwner, Observer { animeForYou ->
            animeListController.setData(animeForYou)
        })

        // Home screen visibility reflects the network state
        viewModel.networkResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    rv_anime.showView()
                    list_main_loading.hideView()
                    list_main_loading_error.hideView()
                }
                is NetworkResponse.Loading -> {
                    rv_anime.hideView()
                    list_main_loading.showView()
                    list_main_loading_error.hideView()
                }
                is NetworkResponse.Error -> {
                    rv_anime.hideView()
                    list_main_loading.hideView()
                    list_main_loading_error.showView()
                }
            }

        })

    }

}
