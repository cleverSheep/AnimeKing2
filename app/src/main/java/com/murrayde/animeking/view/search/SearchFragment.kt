@file:Suppress("LocalVariableName")

package com.murrayde.animeking.view.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animeking.R
import com.murrayde.animeking.network.community.api.AnimeData
import com.murrayde.animeking.util.PagingUtil
import com.murrayde.animeking.view.search.adapter.SearchRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber

class SearchFragment : Fragment() {

    private lateinit var listAdapter: SearchRecyclerViewAdapter
    private lateinit var viewModel: SearchFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SearchRecyclerViewAdapter(activity!!)

        cancel_search.setOnClickListener {
            PagingUtil.RESET_PAGING_OFFSET()
            val action = SearchFragmentDirections.actionSearchFragmentToListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        viewModel = ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
        viewModel.getRequestedAnime().observe(this, Observer<List<AnimeData>> { list_anime ->
            repeat(list_anime.size) {
                Timber.d(list_anime[it].attributes.titles.en
                        ?: list_anime[it].attributes.canonicalTitle)
                Timber.d(list_anime[it].attributes.posterImage.original)
            }
            listAdapter.updateList(list_anime)
        })

        search_rv.adapter = listAdapter
        search_rv.layoutManager = GridLayoutManager(activity!!, 2)

        performSearch(viewModel)
    }

    private fun performSearch(viewModel: SearchFragmentViewModel) {
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                Timber.d("Text changed")
                viewModel.search(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }
}
