@file:Suppress("UNREACHABLE_CODE")

package com.blcs.customview.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.blcs.customview.R

/**
 * 放大镜
 */
class LoupeView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {
    var shapeDrawable :ShapeDrawable? = null
    var radius =200 //放大镜半径
    var show = true
    var SCALE =2 //放大倍数
    var matr:Matrix? = null
    var bitmap :Bitmap? =null
    var bitmap1 :Bitmap? =null
    var widthScale:Float? =null
    var heightScale :Float?=null
    init {
        initAttrs(attrs)
        initUI()
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val obtainStyledAttributes = context?.obtainStyledAttributes(attrs,R.styleable.LoupeView)
        radius = obtainStyledAttributes?.getDimensionPixelSize(R.styleable.LoupeView_radius,100)!!
        SCALE = obtainStyledAttributes?.getInteger(R.styleable.LoupeView_scale, 2)!!
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val wmode = MeasureSpec.getMode(widthMeasureSpec)
        val hmode = MeasureSpec.getMode(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        when(wmode){
            //match_parent
            MeasureSpec.EXACTLY ->{
                widthScale = width.div(bitmap?.width?.toFloat()!!)
            }
            //wrap_content
            MeasureSpec.AT_MOST ->{}
            MeasureSpec.UNSPECIFIED ->{}
        }
        when(hmode){
            //match_parent
            MeasureSpec.EXACTLY ->{
                heightScale = height.div(bitmap?.height?.toFloat()!!)
            }
            //wrap_content
            MeasureSpec.AT_MOST ->{}
            MeasureSpec.UNSPECIFIED ->{}
        }
        if(widthScale!=null&&heightScale!=null){
            val widthS = bitmap?.width?.times(SCALE.times(widthScale!!))
            val heightS = bitmap?.height?.times(SCALE.times(heightScale!!))
            // 放大ImageView图片
            bitmap1 = Bitmap.createScaledBitmap(bitmap,widthS?.toInt()!!,heightS?.toInt()!!,true)
        }else{
            bitmap1 = Bitmap.createScaledBitmap(bitmap,bitmap?.width?.times(SCALE)!!,bitmap?.height?.times(SCALE)!!,true)
        }
        val bitmapShader = BitmapShader(bitmap1,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
        // 给shapeDrawable着色
        shapeDrawable?.paint?.shader = bitmapShader
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun initUI() {
        matr = Matrix()
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        shapeDrawable = ShapeDrawable(OvalShape())
        // 获取ImageView图片
        val bitmapDrawable = drawable as BitmapDrawable
        bitmap = bitmapDrawable.bitmap
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(show) shapeDrawable?.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x?.toInt()
        val y = event?.y?.toInt()
        //绘制shader的起始位置
        matr?.setTranslate(radius.minus(x!!.times(SCALE)).toFloat() ,radius.minus(y!!.times(SCALE)).toFloat())
        shapeDrawable?.paint?.shader?.setLocalMatrix(matr)
        shapeDrawable?.setBounds(Rect(x!!.minus(radius),y!!.minus(radius),x!!.plus(radius),y!!.plus(radius)))
        when (event?.action){
            MotionEvent.ACTION_DOWN ->{  show = true }
            MotionEvent.ACTION_MOVE ->{ }
            MotionEvent.ACTION_UP   ->{ show = false }
        }
        invalidate()
        return true
    }

}
