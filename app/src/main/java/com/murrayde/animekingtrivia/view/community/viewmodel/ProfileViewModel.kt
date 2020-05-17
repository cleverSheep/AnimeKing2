package com.murrayde.animekingtrivia.view.community.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingtrivia.model.player.Player

class ProfileViewModel : ViewModel() {

    private val player_model = MutableLiveData<Player>()
    private val player_name = MutableLiveData<String>()

    fun getProfileUID(): LiveData<Player> = player_model

    fun setProfileUID(player: Player) {
        player_model.value = player
    }

    fun setProfileName(name: String) {
        player_name.value = name
    }

    fun getProfileName(): LiveData<String> = player_name
}