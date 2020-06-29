@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view.search


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.network.community.api.AnimeData
import com.murrayde.animekingmobile.view.search.adapter.SearchRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var listAdapter: SearchRecyclerViewAdapter
    private lateinit var viewModel: SearchFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = SearchRecyclerViewAdapter(requireActivity())
        showKeyboard()

        search_layout.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }

        cancel_search.setOnClickListener {
            hideKeyboard()
            val action = SearchFragmentDirections.actionSearchFragmentToListFragment()
            Navigation.findNavController(it).navigate(action)
        }
        viewModel = ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
        viewModel.getRequestedAnime().observe(requireActivity(), Observer<List<AnimeData>> { list_anime ->
            listAdapter.updateList(list_anime)
        })

        search_rv.adapter = listAdapter
        search_rv.layoutManager = GridLayoutManager(activity, 2)

        performSearch(viewModel)
    }

    private fun showKeyboard() {
        search_edit_text.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(search_edit_text as View, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_layout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun performSearch(viewModel: SearchFragmentViewModel) {
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                viewModel.search(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }
}
