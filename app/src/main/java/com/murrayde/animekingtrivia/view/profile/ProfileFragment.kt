package com.murrayde.animekingtrivia.view.profile


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.util.ImageUtil
import com.murrayde.animekingtrivia.view.community.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        Timber.d("Player:")
        profileViewModel.getProfileUID().observe(viewLifecycleOwner, Observer { player ->
            Timber.d("Player: %s", player.name.toString())
            profile_email.text = player.email.toString()
            ImageUtil.loadImage(profile_photo, player.photo_url)
        })
        profileViewModel.getProfileName().observe(viewLifecycleOwner, Observer {
            profile_name.text = it
        })
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("Fragment is now attached.${activity?.localClassName}")
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
