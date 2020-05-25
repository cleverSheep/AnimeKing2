package com.murrayde.animekingmobile.extensions

import android.view.View

fun View.hideView() {
    visibility = View.INVISIBLE
}

fun View.showView() {
    visibility = View.VISIBLE
}