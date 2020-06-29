package com.murrayde.animekingmobile.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.murrayde.animekingmobile.R
import com.murrayde.animekingmobile.extensions.hideView
import com.murrayde.animekingmobile.extensions.showView
import com.murrayde.animekingmobile.view.community.data_source.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullScreenAll()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        respondToUIVisibilityChanges()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(View(this), InputMethodManager.SHOW_IMPLICIT)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom).setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.answerQuestionFragment, R.id.answerRandomQuestions, R.id.viewResults, R.id.askQuestionFragment, R.id.loginFragment -> {
                    bottom.visibility = View.GONE
                }
                else -> bottom.visibility = View.VISIBLE
            }
        }

        // Home screen visibility reflects the network state
        viewModel.networkDoneLoading().observe(this, Observer { loading ->
            if (loading) {
                activity_main_content.hideView()
                activity_main_loading.showView()
            } else {
                activity_main_content.showView()
                activity_main_loading.hideView()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        fullScreenAll()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /*Screen loses focus when dialog pops up*/
    override fun onResume() {
        super.onResume()
        fullScreenAll()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        fullScreenAll()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun respondToUIVisibilityChanges() {
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                fullScreenAll()
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }

    private fun fullScreenAll() {
        // for lower api versions
        if (Build.VERSION.SDK_INT in 12..18) {
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val v = currentFocus
        if (v != null &&
                (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) &&
                v is EditText &&
                !v.javaClass.name.startsWith("android.webkit.")) {
            val scrcoords = IntArray(2)
            v.getLocationOnScreen(scrcoords)
            val x = ev.rawX + v.getLeft() - scrcoords[0]
            val y = ev.rawY + v.getTop() - scrcoords[1]
            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(activity: Activity?) {
        if (activity != null && activity.window != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }
}
