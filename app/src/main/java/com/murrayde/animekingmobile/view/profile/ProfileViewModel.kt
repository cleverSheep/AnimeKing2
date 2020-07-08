@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.murrayde.animekingmobile.model.player.Player
import com.murrayde.animekingmobile.util.convertToUserName
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val player_model = MutableLiveData<Player>()

    fun getProfileInfoFor(user: FirebaseUser?) {
        user?.let { firebase_user ->
            for (profile in firebase_user.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo URL
                val name = profile.displayName
                val user_name = convertToUserName(profile.email)
                val photoUrl = profile.photoUrl.toString()
                val player = Player(uid, name, user_name, photoUrl)
                player_model.value = player
                Timber.d(Player(uid, name, user_name, photoUrl).toString())
            }
        }
    }

    fun getPlayerInfo(): LiveData<Player> = player_model

}