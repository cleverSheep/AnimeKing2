@file:Suppress("PrivatePropertyName")

package com.murrayde.animeking.view.community.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnimeListMotionStateViewModel : ViewModel() {

    private val START_STATE: MutableLiveData<Int> = MutableLiveData()

    private val motion_state: MutableLiveData<Int> = START_STATE

    init {
        START_STATE.value = 2131296636
    }

    fun setMotionState(state: Int) {
        if (state == -1) motion_state.value = START_STATE.value
        else motion_state.value = state
    }

    fun getMotionState(): Int = motion_state.value!!

}