package com.linkedintools.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView

/**
 * A textview that fades in and out in a loop.
 */
class PulsatingTextView: TextView {
    constructor(context: Context?) : super(context){startPulsating()}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){startPulsating()}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){startPulsating()}

    /**
     * Starts pulsating the text.
     */
    fun startPulsating() {
        val ctx = this
        val animation1 = AlphaAnimation(1.0f, 0.0f)
        val animation2 = AlphaAnimation(0.0f, 1.0f)

        animation1.duration = 1000

        animation1.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationEnd(arg0: Animation) {
                ctx.startAnimation(animation2)
            }

            override fun onAnimationRepeat(arg0: Animation) {
            }

            override fun onAnimationStart(arg0: Animation) {
            }
        })


        animation2.duration = 1000

        animation2.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationEnd(arg0: Animation) {
                ctx.startAnimation(animation1)
            }

            override fun onAnimationRepeat(arg0: Animation) {
            }

            override fun onAnimationStart(arg0: Animation) {
            }
        })

        ctx.startAnimation(animation1)
    }
}