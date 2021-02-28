package com.murrayde.animekingandroid.screen.auth

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.murrayde.animekingandroid.R
import kotlinx.android.synthetic.main.vertical_scrolling_image_view.view.*

/**
 * VerticalScrollingImageView
 * A constraint layout container for an imageView that scrolls up in a loop. The height of the
 * container will change to match the source's aspect ratio.
 *
 * Supported XML properties
 * app:src - set the image source. Currently only supports images with height > width
 * app:scroll_ms - sets the duration of one scroll through the container in milliseconds
 * app:translate_offset - translates the source image up by a percentage
 *
 * Copyright (c) 2019 Pandora Media, Inc.
 */
class VerticalScrollingImageView(
        context: Context,
        private val attributeSet: AttributeSet
) : LinearLayout(context, attributeSet) {
    /*
     * sets the duration of one scroll through the container in milliseconds
     */
    var scrollMs = 0

    /*
     * the image source. Supports resource IDs
     */
    @DrawableRes
    var src: Int = 0
        set(value) {
            field = value
            updateSrc(value)
        }

    /*
     * offsets the source by a percentage of it's height
     */
    private var translateOffset = 0f
    private var initialized = false

    private val animationBin = mutableListOf<ValueAnimator>()

    init {
        LayoutInflater.from(context).inflate(R.layout.vertical_scrolling_image_view, this, true)
        setCustomAttributes()
        orientation = VERTICAL
    }

    fun start() {
        scrollUp(top_image)
        scrollUp(bottom_image)
    }

    fun stop() {
        animationBin.forEach { it.cancel() }
        animationBin.clear()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (initialized) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
        top_image.setImageDrawable(null)
        top_image.setLayerType(View.LAYER_TYPE_NONE, null)
        bottom_image.setImageDrawable(null)
        bottom_image.setLayerType(View.LAYER_TYPE_NONE, null)
    }

    private fun setCustomAttributes() {
        context.obtainStyledAttributes(
                attributeSet,
                R.styleable.VerticalScrollingImageView
        ).apply {

            src = getResourceId(R.styleable.VerticalScrollingImageView_src, 0)
            scrollMs = getInteger(
                    R.styleable.VerticalScrollingImageView_scroll_ms,
                    resources.getInteger(android.R.integer.config_longAnimTime)
            )
            translateOffset = getFloat(
                    R.styleable.VerticalScrollingImageView_translate_offset,
                    0f
            )

            recycle()
        }
    }

    private fun updateSrc(@DrawableRes src: Int) {
        BitmapFactory.decodeResource(resources, src)?.let {
            top_image.setImageBitmap(it)
            bottom_image.setImageBitmap(it)
        }

        // when the src is set from xml, this method is called before the view is measured, so we add scaling
        // and animating the imageViews to the queue
        post {
            scaleToSrcAspectRatio()
            initialized = true
            start()
        }
    }

    /*
    * Resize the imageViews to match the source's aspect ratio while maintaining
    * their width
    */
    private fun scaleToSrcAspectRatio() {
        top_image.drawable?.let {
            // get the aspect ratio of the source image
            val aspectRatio = it.intrinsicHeight.toFloat() / it.intrinsicWidth.toFloat()
            // scale the height to match the source's aspect ratio
            val mHeight = (width * aspectRatio).toInt()

            top_image.setHeight(mHeight)
            bottom_image.setHeight(mHeight)
        }
    }

    private fun scrollUp(view: ImageView) {
        val offset = view.layoutParams.height.toFloat() * translateOffset

        view.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        animationBin.add(ObjectAnimator.ofFloat(-view.layoutParams.height.toFloat()).apply {
            repeatCount = Animation.INFINITE
            repeatMode = ValueAnimator.RESTART
            duration = scrollMs.toLong()
            interpolator = null
            addUpdateListener {
                view.translationY = it.animatedValue as Float - offset
            }
            start()
        })
    }

    private fun View.setHeight(newHeight: Int) {
        layoutParams = layoutParams.apply {
            height = newHeight
        }
    }
}