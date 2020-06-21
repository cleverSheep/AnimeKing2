package com.murrayde.animekingmobile.view.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth

import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.util.ImageUtil
import com.murrayde.animekingmobile.util.convertToUserName
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.getProfileInfoFor(auth.currentUser)
        profileViewModel.getPlayerInfo().observe(viewLifecycleOwner, Observer {
            profile_name.text = it.name
            profile_email.text = convertToUserName(it.email)
            ImageUtil.loadImage(profile_photo, it.photo_url)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_profile_edit.setOnClickListener {
            Toast.makeText(requireActivity(), "Edit profile...", Toast.LENGTH_SHORT).show()
        }
    }
}
