@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.list_anime


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.util.PagingUtil
import com.murrayde.animeking.view.community.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class AnimeList : Fragment() {

    private lateinit var listAdapter: AnimeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            return inflater.inflate(R.layout.fragment_list, container, false)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = AnimeListAdapter()
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        rv_anime.adapter = listAdapter
        rv_anime.layoutManager = GridLayoutManager(activity!!, 3)
        viewModel.animeData.observe(activity!!, Observer<PagedList<AnimeData>> { listAdapter.submitList(it) })

    }

    override fun onDestroy() {
        super.onDestroy()
        PagingUtil.RESET_PAGING_OFFSET()
    }

}
