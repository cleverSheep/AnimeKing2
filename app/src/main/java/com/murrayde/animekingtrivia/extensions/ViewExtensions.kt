package com.murrayde.animekingtrivia.extensions

import android.app.Activity
import android.view.HapticFeedbackConstants
import android.view.View

fun View.hideView() {
    visibility = View.INVISIBLE
}

fun View.showView() {
    visibility = View.VISIBLE
}