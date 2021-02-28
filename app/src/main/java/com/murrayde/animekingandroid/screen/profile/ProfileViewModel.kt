@file:Suppress("LocalVariableName")

package com.murrayde.animekingandroid.screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingandroid.model.player.Player
import com.murrayde.animekingandroid.model.player.PlayerExperience
import com.murrayde.animekingandroid.util.convertToUserName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val player_model = MutableLiveData<Player>()

    private lateinit var playerExperience: PlayerExperience
    private val required_exp = MutableLiveData<Int>()

    private val playerCurrentXPLiveData = MutableLiveData<Int>()
    private val playerXPToLevelUp = MutableLiveData<Int>()
    private val playerCurrentLevel = MutableLiveData<Int>()

    private val db = FirebaseFirestore.getInstance()

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


    fun getPlayerProfileStats(userId: String) {
        GlobalScope.launch {
            val docRef = db.collection("users").document(userId)
            docRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    playerExperience = documentSnapshot.toObject(PlayerExperience::class.java)!!
                    playerCurrentXPLiveData.postValue(playerExperience.total_exp)
                    playerCurrentLevel.postValue(playerExperience.level.toInt())
                    required_exp.postValue(playerExperience.req_exp)
                    playerXPToLevelUp.postValue(playerExperience.req_exp - playerExperience.total_exp)
                    return@addOnSuccessListener
                }
            }
        }
    }
    fun getPlayerCurrentXP(): LiveData<Int> = playerCurrentXPLiveData
    fun getPlayerRequiredXP(): LiveData<Int> = required_exp
    fun getPlayerXPToLevelUp(): LiveData<Int> = playerXPToLevelUp
    fun getPlayerCurrentLevel(): LiveData<Int> = playerCurrentLevel


}