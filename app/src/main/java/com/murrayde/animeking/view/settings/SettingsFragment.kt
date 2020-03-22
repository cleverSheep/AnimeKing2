package com.murrayde.animeking.view.settings


import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.murrayde.animeking.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.screen_settings, rootKey)
    }
}
