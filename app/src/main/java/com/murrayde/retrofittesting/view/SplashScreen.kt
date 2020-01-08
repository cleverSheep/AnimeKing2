package com.murrayde.retrofittesting.view

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.murrayde.retrofittesting.R
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mediaPlayer = MediaPlayer.create(this, R.raw.splash_music)
        Timer().schedule(3500L) {
            runOnUiThread {
                startActivity(Intent(this@SplashScreen, HomeScreenActivity::class.java))
                finish()
            }
        }
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        Timber.d("Music player released")
    }
}
