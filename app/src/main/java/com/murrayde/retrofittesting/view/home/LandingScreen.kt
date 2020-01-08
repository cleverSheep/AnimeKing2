package com.murrayde.retrofittesting.view.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.murrayde.retrofittesting.R
import kotlinx.android.synthetic.main.fragment_landing_screen.*

class LandingScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_landing_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        home_play_button.setOnClickListener {
            val action = LandingScreenDirections.actionLandingScreen2ToMainActivity()
            Navigation.findNavController(it).navigate(action)
        }
        home_credits_button.setOnClickListener {
            val action = LandingScreenDirections.actionLandingScreen2ToCredits()
            Navigation.findNavController(it).navigate(action)
        }
    }

}
