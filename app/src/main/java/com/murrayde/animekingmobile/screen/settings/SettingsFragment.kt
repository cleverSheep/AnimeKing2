@file:Suppress("LocalVariableName")

package com.murrayde.animekingmobile.screen.settings


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.murrayde.animekingmobile.R
import timber.log.Timber


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginManager: LoginManager
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundColor(resources.getColor(R.color.color_background_dark))
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.screen_settings, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        val authPreference = findPreference<Preference>("sign_out")
        loginManager = LoginManager.getInstance()
        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        authPreference?.setOnPreferenceClickListener { _ ->
            Timber.d("Sign out button clicked")
            auth.signOut()
            googleSignInClient.signOut()
            loginManager.logOut()
            val directions = SettingsFragmentDirections.actionMoreToLoginFragment2()
            Navigation.findNavController(requireView()).navigate(directions)
            true
        }

    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(preference: SharedPreferences?, key: String?) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        if (key == "sound_effects") {
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.sound_effects_enabled), preference?.getBoolean("sound_effects", true)!!)
                commit()
            }
        }
        if (key == "vibration") {
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.vibration), preference?.getBoolean("vibration", true)!!)
                commit()
            }
        }
        if (key == "in_game_music") {
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.game_music_enabled), preference?.getBoolean("in_game_music", true)!!)
                commit()
            }
        }

    }

}
