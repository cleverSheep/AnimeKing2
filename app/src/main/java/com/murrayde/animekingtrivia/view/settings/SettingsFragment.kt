@file:Suppress("LocalVariableName")

package com.murrayde.animekingtrivia.view.settings


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.os.LocaleListCompat
import androidx.navigation.Navigation
import androidx.preference.*
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.murrayde.animekingtrivia.R
import com.murrayde.animekingtrivia.util.determineAppLanguage
import com.murrayde.animekingtrivia.view.MainActivity
import timber.log.Timber
import java.util.*


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginManager: LoginManager
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setBackgroundColor(resources.getColor(R.color.color_home_logo))
        Timber.d("onviewcreated stage")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.screen_settings, rootKey)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val current_language = sharedPref.getString("language", "en")
        val language_list_preference = findPreference<ListPreference>("language")
        language_list_preference?.value = determineAppLanguage(current_language!!, LocaleListCompat.getDefault()[0].language)
        Timber.d("Created Preference stage")
        //setDarkThemeBasedOnVersion()
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
        googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        authPreference?.setOnPreferenceClickListener { _ ->
            Timber.d("Sign out button clicked")
            auth.signOut()
            googleSignInClient.signOut()
            loginManager.logOut()
            val directions = SettingsFragmentDirections.actionMoreToLoginFragment2()
            Navigation.findNavController(view!!).navigate(directions)
            Timber.d("onResume stage")
            true
        }

    }


    override fun onPause() {
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        Timber.d("list preference should be paused ")
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        val language_list_preference = findPreference<ListPreference>("language")
        language_list_preference?.value = LocaleListCompat.getDefault()[0].language
        Timber.d("list preference should be destroyed ")
    }

    override fun onSharedPreferenceChanged(preference: SharedPreferences?, key: String?) {

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        Timber.d("Preference settings now initialized")

        if (key == "language") {
            val language = preference?.getString("language", "en")
            Timber.d("Update language: $language")
            setLocale(language)
            with(sharedPref.edit()) {
                putString(getString(R.string.language), preference?.getString("language", language))
                commit()
            }
        }
        if (key == "sound_effects") {
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.sound_effects_enabled), preference?.getBoolean("sound_effects", true)!!)
                commit()
            }
        }
        if (key == "in_game_music") {
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.game_music_enabled), preference?.getBoolean("in_game_music", true)!!)
                commit()
            }
        }

/*        if (key == "dark_mode") {
            val enabled = preference?.getBoolean("dark_mode", true)
            setDarkTheme(enabled!!)
            with(sharedPref.edit()) {
                putBoolean(getString(R.string.dark_theme_enabled), preference.getBoolean("dark_theme_enabled", true))
                commit()
            }
        }*/

    }

    private fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(activity, MainActivity::class.java)
        startActivity(refresh)
    }

    private fun setDarkThemeBasedOnVersion() {
        val dark_mode_pref: SwitchPreferenceCompat? = findPreference("dark_mode")
        dark_mode_pref?.isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

/*    private fun setDarkTheme(enabled: Boolean) {
        if (enabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val refresh = Intent(activity, MainActivity::class.java)
        startActivity(refresh)

    }*/

}
