package com.murrayde.animekingmobile.screen.profile


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.murrayde.animekingmobile.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber

@AndroidEntryPoint
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
        profileViewModel.getPlayerProfileStats(auth.uid!!)
        profileViewModel.getPlayerInfo().observe(viewLifecycleOwner, Observer { player ->
//            profile_name.text = player.name
            profile_user_name.text = player.user_name
            //ImageUtil.loadImage(profile_photo, player.photo_url)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("progress max: ${profileProgressBarSimpleCustom.max}")
        profileViewModel.getPlayerCurrentLevel().observe(viewLifecycleOwner, Observer {
            profileImageLevel.text = "Form $it"
            current_level.text = "$it"
            next_level.text = "${it + 1}"
        })
        profileViewModel.getPlayerRequiredXP().observe(viewLifecycleOwner, Observer {required_experience ->
            level_up_xp.text = "/${required_experience}"
            profileProgressBarSimpleCustom.max = required_experience.toFloat()
        })
        profileViewModel.getPlayerCurrentXP().observe(viewLifecycleOwner, Observer{ xp ->
            Handler(Looper.getMainLooper()).postDelayed({
                profileProgressBarSimpleCustom.progress = xp.toFloat()
                profileProgressBarSimpleCustom.secondaryProgress += xp + 20
            }, 75)
            current_xp.text = "XP $xp"
        })
        profileViewModel.getPlayerXPToLevelUp().observe(viewLifecycleOwner, Observer {
            required_xp.text = "$it XP "
        })
    }

}
