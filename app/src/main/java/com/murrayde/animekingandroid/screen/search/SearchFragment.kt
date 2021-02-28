@file:Suppress("LocalVariableName")

package com.murrayde.animekingandroid.screen.search


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.murrayde.animekingandroid.R
import com.murrayde.animekingandroid.extensions.hideView
import com.murrayde.animekingandroid.extensions.showView
import com.murrayde.animekingandroid.model.ui.NetworkResponse
import com.murrayde.animekingandroid.screen.search.adapter.SearchRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var listAdapter: SearchRecyclerViewAdapter
    private val viewModel: SearchFragmentViewModel by viewModels()

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

        viewModel.networkResponse.observe(requireActivity(), Observer { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    search_rv.showView()
                    search_loading_anim.hideView()
                    list_main_loading_error.hideView()
                }
                is NetworkResponse.Loading -> {
                    search_loading_anim.showView()
                    search_rv.hideView()
                    list_main_loading_error.hideView()
                }
                is NetworkResponse.Error -> {
                    list_main_loading_error.showView()
                    search_rv.hideView()
                    search_loading_anim.hideView()
                }
            }
        })
        viewModel.getRequestedAnime().observe(requireActivity(), Observer { list_anime ->
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
