@file:Suppress("PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_anime


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.view.community.data_source.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*

@AndroidEntryPoint
class AnimeList : Fragment() {

    private val viewModel: MainActivityViewModel by viewModels()

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

    }

}
