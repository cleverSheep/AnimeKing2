package com.murrayde.animekingmobile.view.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.murrayde.animekingmobile.model.player.Player
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val player_model = MutableLiveData<Player>()
    private val player_name = MutableLiveData<String>()

    fun getProfileInfoFor(user: FirebaseUser?) {
        user?.let { firebase_user ->
            for (profile in firebase_user.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo URL
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl.toString()
                val player = Player(uid, name, email, photoUrl)
                player_model.value = player
                Timber.d(Player(uid, name, email, photoUrl).toString())
            }
        }
    }

    fun getPlayerInfo(): LiveData<Player> = player_model

    fun setProfileUID(player: Player) {
        player_model.value = player
    }

    fun setProfileName(name: String) {
        player_name.value = name
    }

    fun getProfileName(): LiveData<String> = player_name
}