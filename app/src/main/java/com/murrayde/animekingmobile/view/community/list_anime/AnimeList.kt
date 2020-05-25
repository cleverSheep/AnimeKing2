@file:Suppress("PrivatePropertyName", "LocalVariableName")

package com.murrayde.animekingmobile.view.community.list_anime


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.hideView
import com.murrayde.animekingmobile.extensions.showView
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.util.PagingUtil
import com.murrayde.animekingmobile.view.community.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber

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

        rv_anime.apply {
            adapter = listAdapter.adapter
            layoutManager = GridLayoutManager(activity!!, 3)
            (layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when (position) {
                    0 -> 3
                    else -> 1
                }
            }
        }
        viewModel.animeData.observe(activity!!, Observer<PagedList<AnimeData>> { listAdapter.submitList(it) })

        viewModel.networkDoneLoading().observe(activity!!, Observer { loading ->
            if (loading) {
                rv_anime.hideView()
                list_main_loading.showView()
            } else {

                rv_anime.showView()
                list_main_loading.hideView()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        PagingUtil.RESET_PAGING_OFFSET()
        Timber.d("List destroyed")
    }

}
