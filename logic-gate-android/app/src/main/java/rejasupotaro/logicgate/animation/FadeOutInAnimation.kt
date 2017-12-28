package rejasupotaro.logicgate.animation

import android.view.animation.AlphaAnimation
import android.view.animation.Animation

class FadeOutInAnimation : AlphaAnimation {
    constructor(listener: () -> Unit = {}) : this(listener, 1f, 0f)
    constructor(listener: () -> Unit, fromAlpha: Float, toAlpha: Float) : super(fromAlpha, toAlpha) {
        duration = 100
        repeatCount = 1
        repeatMode = Animation.REVERSE
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {
                listener()
            }
        })
    }
}

