package com.murrayde.animekingtrivia.view.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.murrayde.animekingtrivia.model.player.Player

class ProfileViewModel : ViewModel() {

    private val player_model = MutableLiveData<Player>()

    fun getProfileUID(): MutableLiveData<Player> = player_model

    fun setProfileUID(player: Player) {
        player_model.value = player
    }
}