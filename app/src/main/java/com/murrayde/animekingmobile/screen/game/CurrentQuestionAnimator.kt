package com.murrayde.animekingmobile.screen.game

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.murrayde.animekingmobile.R
import kotlinx.android.synthetic.main.current_question_animator.view.*

class CurrentQuestionAnimator(context: Context, private val attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var rectColor = 0
    private var currentState = 0

    private var animationStates: Array<CurrentQuestionView>
    private var animationBin = mutableListOf<ValueAnimator>()

    init {
        LayoutInflater.from(context).inflate(R.layout.current_question_animator, this, true)
        setCustomAttributes()
        animationStates = arrayOf(animator_one, animator_two, animator_three, animator_four, animator_five, animator_six, animator_seven, animator_eight, animator_nine, animator_ten)
    }

    private fun setCustomAttributes() {
        context.obtainStyledAttributes(
                attributeSet,
                R.styleable.CurrentQuestionAnimator
        ).apply {
            rectColor = getColor(R.styleable.CurrentQuestionAnimator_rect_color, Color.GREEN)
            currentState = getInteger(R.styleable.CurrentQuestionAnimator_current_state, 0)
            recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        blinkAnimation(animationStates[currentState])
    }

    fun nextQuestion() {
        animationStates[currentState].setColor(resources.getColor(R.color.color_positive_dark))
        animationBin[currentState].end()
        if (currentState + 1 >= animationStates.size) {
            currentState = (currentState + 1) % animationStates.size
            animationStates.forEach { it.setColor(Color.GRAY) }
            animationBin.forEach {
                it.cancel()
            }
            animationBin.clear()
        } else {
            currentState += 1
        }
        blinkAnimation(animationStates[currentState])
        postInvalidate()
    }

    private fun blinkAnimation(view: CurrentQuestionView) {
        val colorAnimation =
                ValueAnimator.ofObject(ArgbEvaluator(), Color.GRAY, resources.getColor(R.color.color_positive_dark))
        colorAnimation.apply {
            duration = 500L
            addUpdateListener { animator -> view.setColor(animator.animatedValue as Int) }
            repeatCount = INFINITE
            repeatMode = REVERSE
            start()
        }
        animationBin.add(colorAnimation)
    }
}