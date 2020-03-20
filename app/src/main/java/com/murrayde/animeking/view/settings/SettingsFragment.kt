package com.murrayde.animeking.view.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth

import com.murrayde.animeking.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings_sign_out.setOnClickListener { btn_view ->
            FirebaseAuth.getInstance().signOut()
            val directions = SettingsFragmentDirections.actionMoreToAuthenticationActivity()
            Navigation.findNavController(view).navigate(directions)
        }
    }
}
