package com.murrayde.animekingandroid.screen.main.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.murrayde.animekingandroid.R
import kotlinx.android.synthetic.main.fragment_low_question_count.*

class LowQuestionCount : Fragment() {

    private val args: AnimeListDetailArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_low_question_count, container, false)
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
