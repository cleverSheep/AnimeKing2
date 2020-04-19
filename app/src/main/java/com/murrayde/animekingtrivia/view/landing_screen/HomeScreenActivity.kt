package com.murrayde.animekingtrivia.view.landing_screen

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.murrayde.animekingtrivia.R

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }
}
