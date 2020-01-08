package com.murrayde.retrofittesting.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.murrayde.retrofittesting.R
import kotlinx.android.synthetic.main.fragment_credits.*

class Credits : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_credits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        credits_tv.setOnClickListener {
            val action = CreditsDirections.actionCreditsToLandingScreen2()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
