package com.murrayde.animekingtrivia.view.landing_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.murrayde.animekingtrivia.R

class Credits : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_credits, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_credits)
        toolbar.setupWithNavController(findNavController())
        return view
    }
}
