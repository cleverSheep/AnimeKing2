package com.murrayde.animeking.view.settings


import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.preference.PreferenceFragmentCompat
import com.murrayde.animeking.R
import com.murrayde.animeking.view.MainActivity
import java.util.*


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.screen_settings, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    override fun onSharedPreferenceChanged(preference: SharedPreferences?, key: String?) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        if (key == "language") {
            val language = preference?.getString("language", "")
            setLocale(language)
        }
        if(key == "sound_effects") {
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.sound_effects_enabled), preference?.getBoolean("sound_effects", true)!!)
                commit()
            }
        }
        if(key == "in_game_music") {
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.game_music_enabled), preference?.getBoolean("in_game_music", true)!!)
                commit()
            }
        }

        if(key == "dark_mode") {
            with (sharedPref.edit()) {
                putBoolean(getString(R.string.dark_theme_enabled), preference?.getBoolean("dark_theme_enabled", true)!!)
                commit()
            }
        }

    }

    fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(activity, MainActivity::class.java)
        startActivity(refresh)
    }

}
