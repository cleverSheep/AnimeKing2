package com.murrayde.animekingmobile.extensions

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murrayde.animekingmobile.R

fun View.hideView() {
    visibility = View.INVISIBLE
}

fun View.showView() {
    visibility = View.VISIBLE
}

/**
 * Returns true if the navigation controller is still pointing at 'this' fragment, or false if it already navigated away.
 */
fun Fragment.mayNavigate(): Boolean {

    val navController = findNavController()
    val destinationIdInNavController = navController.currentDestination?.id
    val destinationIdOfThisFragment = view?.getTag(R.id.fragment_list)
            ?: destinationIdInNavController

    // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
    return if (destinationIdInNavController == destinationIdOfThisFragment) {
        view?.setTag(R.id.fragment_list, destinationIdOfThisFragment)
        true
    } else {
        Log.d("FragmentExtensions", "May not navigate: current destination is not the current fragment.")
        false
    }
}