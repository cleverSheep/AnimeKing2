package com.murrayde.animekingmobile.screen.main.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.screen.main.detail.AnimeListDetailArgs
import com.murrayde.animekingmobile.screen.main.detail.LowQuestionCountDirections
import kotlinx.android.synthetic.main.fragment_low_question_count.*

class LowQuestionCount : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_low_question_count, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_low_questions)
        toolbar.setupWithNavController(findNavController())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val attributes = args.animeAttributes

        button_transition_ask_question.setOnClickListener {
            val action = LowQuestionCountDirections.actionLowQuestionCountFragmentToAskQuestionFragment(attributes)
            Navigation.findNavController(view).navigate(action)
        }

        button_transition_return_home.setOnClickListener {
            val action = LowQuestionCountDirections.actionLowQuestionCountFragmentToHome()
            Navigation.findNavController(view).navigate(action)
        }
    }

}
