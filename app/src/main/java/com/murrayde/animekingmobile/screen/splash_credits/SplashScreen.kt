package com.murrayde.animekingmobile.screen.splash_credits

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.screen.MainActivity
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        fullScreenAll()

        mediaPlayer = MediaPlayer.create(this, R.raw.splash_music)
        Timer().schedule(4500) {
            runOnUiThread {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        }
        mediaPlayer.start()
    }

    private fun fullScreenAll() {
        if (Build.VERSION.SDK_INT in 12..18) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        Timber.d("Music player released")
    }
}
