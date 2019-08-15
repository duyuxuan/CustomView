package com.blcs.kotlinstudy.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.blcs.customview.R

class ScratchCardView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {
    var mPaint: Paint?
    var preX: Float? = null
    var preY: Float? = null
    var path: Path?

    var size = 50f //橡皮擦大小
    var bitmap: Bitmap? = null
    var wScale: Float? = null
    var hScale: Float? = null

    init {
        //        关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        initAttrs(context, attrs)
        mPaint = Paint()?.apply {
            color = Color.GRAY
            strokeWidth = size
            //抗锯齿
            flags = Paint.ANTI_ALIAS_FLAG
        }
        //路径
        path = Path()
    }

    private fun initAttrs(context: Context?, attrs: AttributeSet?) {
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.ScratchCard)
        size = typeArray?.getFloat(R.styleable.ScratchCard_fingerSize, 50f)!!
        val drawable = typeArray?.getDrawable(R.styleable.ScratchCard_preBackground)
        drawable?.let {
            val bitmapDrawable = drawable as BitmapDrawable
            bitmap = bitmapDrawable.bitmap
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wmode = MeasureSpec.getMode(widthMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        when (wmode) {
            //match_parent
            MeasureSpec.EXACTLY,MeasureSpec.AT_MOST -> {
                //把前景图宽度缩放到具体的尺寸
                if(bitmap!=null)  wScale = width.toFloat().div(bitmap?.width!!)
            }
            //wrap_content
            MeasureSpec.UNSPECIFIED -> { }
        }
        val hmode = MeasureSpec.getMode(heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        when (hmode) {
            //match_parent
            MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                //把前景图高度缩放到具体的尺寸
                if(bitmap!=null) hScale = height.toFloat().div(bitmap?.height!!)
            }
            MeasureSpec.UNSPECIFIED -> { }
        }
        if (wScale != null && hScale != null) {
            val widthS = bitmap?.width?.times(wScale!!)
            val heightS = bitmap?.height?.times(hScale!!)
            bitmap = Bitmap.createScaledBitmap(bitmap, widthS!!.toInt(), heightS!!.toInt(), true)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //创建新图层
        val saveLayer = canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        //画前景图
        if(bitmap!=null){
            canvas?.drawBitmap(bitmap,0f,0f,mPaint)
        }else{
            mPaint?.style = Paint.Style.FILL_AND_STROKE
            canvas?.drawRect(Rect(0,0,width,height), mPaint)
        }
        //设置混合模式
        mPaint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        //绘制手势路径
        mPaint?.style = Paint.Style.STROKE
        canvas?.drawPath(path,mPaint)
        //清空混合模式
        mPaint?.setXfermode(null)
        canvas?.restoreToCount(saveLayer!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                path?.moveTo(event.x, event.y)
                preX = event.x
                preY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = (preX?.plus(event.x))?.div(2)
                val endY = (preY?.plus(event.y))?.div(2)
                path?.quadTo(preX!!, preY!!, endX!!, endY!!)
                preX = event.x
                preY = event.y

            }
            MotionEvent.ACTION_UP -> {
            }
        }
        postInvalidate()

        return super.onTouchEvent(event)
    }

}