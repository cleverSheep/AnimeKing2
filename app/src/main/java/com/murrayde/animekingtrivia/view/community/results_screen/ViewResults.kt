@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.view.community.results_screen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.view.community.results_screen.review_questions.ReviewQuestionsAdapter
import kotlinx.android.synthetic.main.fragment_view_results.*
import kotlinx.android.synthetic.main.fragment_view_results_bottom_sheet.*


class ViewResults : Fragment() {

    private val args: ViewResultsArgs by navArgs()
    private lateinit var linearLayoutBottomSheet: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list_questions = args.listQuestions
        val review_questions_adapter = ReviewQuestionsAdapter(activity!!, list_questions!!)

        bottom_sheet_rv.adapter = review_questions_adapter
        bottom_sheet_rv.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)

        linearLayoutBottomSheet = view.findViewById(R.id.results_bottom_sheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutBottomSheet)

        results_toggle_button.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            else bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(view: View, new_state: Int) {
                if (new_state == BottomSheetBehavior.STATE_EXPANDED) results_toggle_button.isChecked = true
                else if (new_state == BottomSheetBehavior.STATE_COLLAPSED) {
                    results_toggle_button.isChecked = false
                }
            }

        })

        results_quit_game.setOnClickListener { v ->
            val direction = ViewResultsDirections.actionViewResultsToDetailFragment(args.animeAttributes)
            Navigation.findNavController(v).navigate(direction)
        }
    }
}
