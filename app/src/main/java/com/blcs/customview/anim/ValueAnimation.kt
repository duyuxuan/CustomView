package com.blcs.customview.anim

import android.animation.ValueAnimator
import android.view.View

object ValueAnimation {
    /**
     * ValueAnimator常用函数
     * ofInt(int... values)、ofFloat(float... values) 、ofObject：设置动画变化过程中的值
     * setDuration(long duration)：设置动画时长，单位是毫秒
     * getAnimatedValue()：获取ValueAnimator在运动时当前运动点的值
     * start()：开始动画
     * setRepeatCount(int value)：设置循环次数，设置为INFINITE表示无限循环
     * setRepeatMode(int value)：设置循环模式 ValueAnimation.REVERSE倒序重新开始 ValueAnimation.RESTART正序重新开始
     * addUpdateListener(AnimatorUpdateListener listener) ： 监听动画过程中值的实时变化
     * addListener(AnimatorListener listener)：监听动画变化时的4个状态
     * removeUpdateListener(AnimatorUpdateListener listener)：移除指定监听
     * removeAllUpdateListeners()：移除所有监听
     * removeListener(AnimatorListener listener)：移除AnimatorUpdateListener
     * removeAllListeners()：移除AnimatorListener
     * setInterpolator()：设置插值器
     * cancel()：取消动画
     * setStartDelay(long startDelay)：延时多久开始，单位是毫秒
     * clone()：克隆一个ValueAnimator实例
     */
    fun init(view : View):ValueAnimator{
        val animator = ValueAnimator.ofInt(0, 500)

        animator.duration = 3000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.setEvaluator(MyEvaluator)
        animator.addUpdateListener({animation ->
            val value = animation.animatedValue as Int
            view.layout(view.left,view.top,value+view.left,view.bottom)
        })
        return animator
    }

    /**
     * 开始动画
     */
    fun startAnimator(view : View){
        val init = init(view)
        init.start()
    }

    /**
     * 停止动画
     */
    fun  stopAnimator(view : View){
        val init = init(view)
        init.removeAllUpdateListeners()
        init.cancel()
    }
}