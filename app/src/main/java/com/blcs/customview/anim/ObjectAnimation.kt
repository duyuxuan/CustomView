package com.blcs.customview.anim

import android.animation.*
import android.animation.ValueAnimator.REVERSE
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.support.v4.view.ViewCompat.animate
import android.animation.ObjectAnimator




object ObjectAnimation {
    /**
     * 组合动画
     */
    fun start(tv1 : View){
        var background = ObjectAnimator.ofInt(tv1,"BackgroundColor", Color.BLACK,Color.BLUE,Color.RED)
        background.setRepeatCount(ValueAnimator.INFINITE)
        var alpha = ObjectAnimator.ofFloat(tv1,"alpha",0f,1f,0f,0.5f)
        alpha.setRepeatCount(ValueAnimator.INFINITE)
        var animatorSet = AnimatorSet()
        animatorSet.playTogether(background,alpha)
        animatorSet.setDuration(3000)
        animatorSet.start()
    }

    /**
     * PropertyValuesHolder 保存了动画过程中所需要操作的属性和对应的值。
     * //设置动画的Evaluator
     * public void setEvaluator(TypeEvaluator evaluator)
     * //用于设置ofFloat()所对应的动画值列表
     * public void setFloatValues(float... values)
     * //用于设置ofInt()所对应的动画值列表
     * public void setIntValues(int... values)
     * //用于设置ofKeyframes()所对应的动画值列表
     * public void setKeyframes(Keyframe... values)
     * //用于设置ofObject()所对应的动画值列表
     * public void setObjectValues(Object... values)
     * //设置动画属性名
     * public void setPropertyName(String propertyName)
     */
    fun property(view : View){
        val Background = PropertyValuesHolder.ofInt("BackgroundColor", Color.BLACK,Color.BLUE,Color.RED)
        val alpha = PropertyValuesHolder.ofFloat("alpha",0f,1f,0f,0.5f)
        val holder = ObjectAnimator.ofPropertyValuesHolder(view,Background, alpha)
        holder.duration = 3000
        holder.interpolator = AccelerateInterpolator()
        holder.repeatCount = Animation.INFINITE
        holder.repeatMode = REVERSE
        holder.start()
    }
    /**
     * KeyFrame 提供方便地控制动画速率问题。
     */
    fun keyFrame(view : View){
        val holder = PropertyValuesHolder.ofFloat("alpha", 0f,1f)
        //fraction表示当前的显示进度  value:表示动画当前所在的数值位置。
        val keyframe = Keyframe.ofFloat(0.1f, 0.1f)
        PropertyValuesHolder.ofKeyframe("alpha",keyframe)
        val valuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, holder)
        valuesHolder.duration = 3000
        valuesHolder.repeatCount = Animation.INFINITE
        valuesHolder.repeatMode = REVERSE
        valuesHolder.start()
    }

    /**
     * ViewPropertyAnimator Android3.1中新增ViewPropertyAnimator机制，给默认属性提供了一种更加便捷的用法。
     */
    fun viewProperty(view : View){
        view.animate().alpha(0.5f).translationX(500f).rotation(180f).setDuration(5000).scaleX(2f)
    }
}