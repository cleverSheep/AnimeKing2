package com.murrayde.animekingandroid.screen.splash_credits

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.murrayde.animekingandroid.R

class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }
}
