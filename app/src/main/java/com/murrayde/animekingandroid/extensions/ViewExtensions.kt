package com.murrayde.animekingandroid.extensions

import android.animation.Animator
import android.animation.AnimatorSet
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murrayde.animekingandroid.R

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

fun View.animateQuizItems(): Animator {
    val scaleX = android.animation.PropertyValuesHolder.ofFloat(View.SCALE_X, 0.75f, 1f)
    val scaleY = android.animation.PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.75f, 1f)
    val alpha = android.animation.PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

    return android.animation.ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY, alpha).apply {
        duration = 200
        interpolator = android.view.animation.LinearInterpolator()
    }
}

fun View.reverseAnimate(): Animator {
    val scaleX = android.animation.PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0.75f)
    val scaleY = android.animation.PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0.75f)
    val alpha = android.animation.PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 0f)

    val verticalAnimation = android.animation.ObjectAnimator.ofPropertyValuesHolder(this, scaleY).apply {
        duration = 750
        interpolator = android.view.animation.LinearInterpolator()
    }
    val horizontalAnimation = android.animation.ObjectAnimator.ofPropertyValuesHolder(this, scaleX).apply {
        duration = 750
        interpolator = android.view.animation.LinearInterpolator()
    }
    val fadeOutAnimation = android.animation.ObjectAnimator.ofPropertyValuesHolder(this, alpha).apply {
        duration = 750
        interpolator = android.view.animation.LinearInterpolator()
    }
    return AnimatorSet().apply {
        playTogether(verticalAnimation, horizontalAnimation, fadeOutAnimation)
    }
}