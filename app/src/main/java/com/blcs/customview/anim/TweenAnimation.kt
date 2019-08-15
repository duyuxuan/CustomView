package com.blcs.customview.anim

import android.view.animation.*

/**
 * Tween动画代码实现
 */
object TweenAnimation {
    fun getScale(): Animation {
        val scaleAnimation = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f)
        return setMode(scaleAnimation)
    }

    fun getAlpha(): Animation {
        val alphaAnimation = AlphaAnimation(0f, 1f)
        return setMode(alphaAnimation)
    }
    fun getRotate(): Animation {
        val rotateAnimation = RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f)
        return setMode(rotateAnimation)
    }
    fun getTranslate(): Animation {
        val translatelAnimation = TranslateAnimation(0f,1000f,0f,0f)
        return setMode(translatelAnimation)
    }
    fun getSet(): Animation {
        val scaleAnimation = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
            Animation.RELATIVE_TO_SELF,0.5f,
            Animation.RELATIVE_TO_SELF,0.5f)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        val rotateAnimation = RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f)
        val translatelAnimation = TranslateAnimation(0f,1000f,0f,0f)
        setMode(scaleAnimation)
        setMode(alphaAnimation)
        setMode(rotateAnimation)
        setMode(translatelAnimation)
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(scaleAnimation)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(translatelAnimation)
        return animationSet
    }
    fun setMode(animation: Animation): Animation {
        animation.duration=3000
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        return animation
    }
}