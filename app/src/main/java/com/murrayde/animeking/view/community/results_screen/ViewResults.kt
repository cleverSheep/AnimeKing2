@file:Suppress("LocalVariableName")

package com.murrayde.animeking.view.community.results_screen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.murrayde.animeking.R

class ViewResults : Fragment() {

    private val args: ViewResultsArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list_questions = args.listQuestions
    }

    private fun questionCorrectStatus(): HashMap<Int, Boolean> {
        var hashmap_correct = HashMap<Int, Boolean>()
        val bundle = this.arguments
        if(bundle?.getSerializable("hashmap_correct") != null){
            hashmap_correct = bundle.getSerializable("hashmap_correct") as HashMap<Int, Boolean>
        }
        return hashmap_correct
    }
}
