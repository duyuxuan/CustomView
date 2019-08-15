package com.blcs.customview.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.lang.reflect.Array.getLength
import android.util.Log


class PathMeasureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint? = null
    init {
        //创建画笔
        paint = Paint()
        //设置画笔颜色
        paint?.setColor(Color.WHITE)
        //设置填充样式 1.Paint.Style.FILL仅填充内部  2.Paint.Style.FILL_AND_STROKE填充内部和描边 3.Paint.Style.STROKE仅描边
        paint?.setStyle(Paint.Style.STROKE)
        //设置画笔宽度 注：画笔样式为Paint.Style.FILL时不显示效果
        paint?.setStrokeWidth(5f)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.BLACK)
        val path = Path()
        path.addRect(50f, 50f, 150f, 150f, Path.Direction.CW)
        path.addRect(200f, 200f, 300f, 300f, Path.Direction.CW)
        canvas?.drawPath(path, paint)
        val pathMeasure = PathMeasure(path, false)
        //获取计算的路径长度
        val length = pathMeasure.length
        //用于判断测量Path时是否计算闭合
        val closed = pathMeasure.isClosed
        //用于跳转到下一条曲线的函数
        val nextContour = pathMeasure.nextContour()

//用于截取整个Path中的某个片段，通过参数startD和stopD来控制截取的长度，并将截取后
//的Path保存到参数dst中。最后一个参数startWithMoveTo表示起始点是否使用moveTo将路径的
//新起始点移到结果Path的起始点，通常设置为true,以保证每次截取的Path都是正常的，完整的，
//通常和dst一起使用，因为dst中保存的Path是被不断添加的，而不是每次被覆盖的。
        pathMeasure.getSegment(50f,50f,path,true)
//用于得到路径上某一长度的位置以及该位置的正切值。
//distance:距离Path起始点的长度，取值范围为0<=distance<=getLength。
//pos：该点的坐标值。     tan:该点的正切值。
//        pathMeasure.getPosTan()
//用于得到路径上某一长度的位置以及该位置的正切值的矩阵
//        public boolean getMatrix(float distance,Matrix matrix,int flags);
    }
}