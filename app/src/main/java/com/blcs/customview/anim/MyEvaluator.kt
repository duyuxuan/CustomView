package com.blcs.customview.anim

import android.animation.TypeEvaluator
/**
 * 自定义Evaluator
 */
object MyEvaluator : TypeEvaluator<Int>{
    /**
     * fraction 参数就是插值器中的返回值，表示当前动画的数值进度，以百分制的小数表示
     * startValue 和endValue分别对应ofInt(int start,int end)函数中start和end的数值。
     * 返回值 就是当前数值进度所对应的具体数值。
     */
    override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
        val value = startValue + fraction * (endValue - startValue)*2
        return value.toInt()
    }
}