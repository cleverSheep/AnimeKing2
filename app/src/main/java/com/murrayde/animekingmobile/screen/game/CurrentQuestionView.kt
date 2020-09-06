package com.murrayde.animekingmobile.screen.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.murrayde.animekingmobile.R

class CurrentQuestionView : View {

    private val DEFAULT_COLOR = Color.DKGRAY

    private lateinit var mPaint: Paint
    private lateinit var mRect: RectF
    private var mRectColor: Int = 0

    companion object {
        private var mPadding = 0
        private val originX = 0
        private val originY = 0
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        if (set == null) return

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mRect = RectF()

        val typedArray = context.obtainStyledAttributes(set, R.styleable.CurrentQuestionView)
        mRectColor = typedArray.getColor(R.styleable.CurrentQuestionView_square_color, DEFAULT_COLOR)
        mPaint.color = mRectColor
        typedArray.recycle()
    }

    fun setColor(color: Int) {
        mPaint.color = color
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setViewBounds()
        canvas.drawRoundRect(mRect, 100f, 100f, mPaint)
    }

    private fun setViewBounds() {
        mRect.left = (originX + mPadding).toFloat()
        mRect.right = (width - mPadding).toFloat()
        mRect.top = (originY + mPadding).toFloat()
        mRect.bottom = (height - mPadding).toFloat()
    }
}
