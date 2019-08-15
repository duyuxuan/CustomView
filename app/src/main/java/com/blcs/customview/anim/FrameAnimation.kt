package com.blcs.customview.anim

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import java.security.AccessControlContext

object FrameAnimation {
    /**
     * AnimationDrawable常用属性
     * start()：开始播放逐帧动画。
     * stop()：停止播放逐帧动画。
     * getDuration(int index)：得到指定index的帧的持续时间
     * getFrame(int index)：得到指定index的帧所对应的Drawable对象
     * getNumberOfFrames()：得到当前AnimationDrawable的所有帧数量
     * isRunning()：判断当前AnimationDrawable是否正在播放
     * setOneShot(boolean oneShot)：设置AnimationDrawable是否执行一次
     * isOneShot()：判断当前AnimationDrawable是否执行一次
     * addFrame(Drawable frame,int duration)：为AnimationDrawable添加1帧，并设置持续时间
     */
    fun getAnimation(context: Context):AnimationDrawable{
        val animationDrawable = AnimationDrawable()
        for (x in 1..3){
            val resources = context.resources.getIdentifier("frame"+x,"mipmap",context.packageName)
            val drawable = context.resources.getDrawable(resources)
            animationDrawable.addFrame(drawable,500)
        }
        animationDrawable.isOneShot = false
        return animationDrawable
    }
}