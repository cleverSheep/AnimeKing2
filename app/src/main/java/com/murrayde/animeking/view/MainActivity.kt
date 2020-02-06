package com.murrayde.animeking.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

import androidx.appcompat.app.AppCompatActivity

import com.murrayde.animeking.R
import com.murrayde.animeking.util.PagingUtil
import com.murrayde.animeking.util.QuestionUtil
import com.murrayde.animeking.view.community.list_anime.AnimeList
import com.murrayde.animeking.view.profile.ProfileFragment
import com.murrayde.animeking.view.search.SearchFragment
import com.murrayde.animeking.view.settings.SettingsFragment
import com.pandora.bottomnavigator.BottomNavigator

class MainActivity : AppCompatActivity() {
    private lateinit var navigator: BottomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(View(this), InputMethodManager.SHOW_IMPLICIT)

        navigator = BottomNavigator.onCreate(
                fragmentContainer = R.id.nav_host_fragment,
                bottomNavigationView = findViewById(R.id.bottomnav_view),
                rootFragmentsFactory = mapOf(
                        R.id.menu_nav_home to { AnimeList() },
                        R.id.menu_nav_search to { SearchFragment() },
                        R.id.menu_nav_profile to { ProfileFragment() },
                        R.id.menu_nav_settings to { SettingsFragment() }
                ),
                defaultTab = R.id.menu_nav_home,
                activity = this
        )
    }

    override fun onBackPressed() {
        if (!navigator.pop()) {
            super.onBackPressed()
        }
    }
}
