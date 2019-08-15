package com.blcs.customview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomBaseView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var paint: Paint? = null
    private var paint1: Paint? = null

    init {
        initPaint()
    }


    fun initPaint(){
        //创建画笔
        paint = Paint()
        //设置画笔颜色
        paint?.setColor(Color.WHITE)
        //设置填充样式 1.Paint.Style.FILL仅填充内部  2.Paint.Style.FILL_AND_STROKE填充内部和描边 3.Paint.Style.STROKE仅描边
        paint?.setStyle(Paint.Style.STROKE)
        //设置画笔宽度 注：画笔样式为Paint.Style.FILL时不显示效果
        paint?.setStrokeWidth(5f)

        paint1 = Paint()
        //设置画笔颜色
        paint1?.setColor(Color.GREEN)
        //设置填充样式 1.Paint.Style.FILL仅填充内部  2.Paint.Style.FILL_AND_STROKE填充内部和描边 3.Paint.Style.STROKE仅描边
        paint1?.setStyle(Paint.Style.STROKE)
        //设置画笔宽度 注：画笔样式为Paint.Style.FILL时不显示效果
        paint1?.setStrokeWidth(5f)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //平移画布 起始点起始点从（X:0,Y:0）变成（X:100,Y:100）
        canvas?.translate(0f, 100f)
        //裁剪画布 构造方法很多 都是以clip开头
        canvas?.clipRect(0, 0, 500, 500)
        //设置画布颜色
        canvas?.drawColor(Color.BLACK)
//        canvas.drawARGB(0,0,0,255);
//        canvas.drawRGB(0,0,255);

//        绘制直线 startX/startY:起始点X/Y坐标  stopX/stopY:终点X/Y坐标
        canvas?.drawLine(100f, 50f, 450f, 50f, paint)
        //绘制点
        canvas?.drawPoint(50f, 50f, paint)

        //绘制矩形====== >
        val rect = Rect(50, 100, 450, 450)//创建矩形工具类
        //绘制矩形方法
        canvas?.drawRect(rect, paint)//第一种构造方法
//        Rect/RectF用来保存int/float类型数值的矩形结构
//        RectF rectf = new RectF(200,200,400,400);
//        canvas.drawRect(rectf);第二种构造方法
//        canvas.drawRect(200,200,400,400,paint);第三种构造方法
        //   <====== 绘制矩形方法

//      起始点变成（X:50,Y:100）
        canvas?.translate(50f, 100f)
        //绘制路径 ====== >
        val path = Path()
        //设置起始点
        path.moveTo(100f, 50f)
        //第一条直线的终点也是第二条直线的起点
        path.lineTo(50f, 200f)
        path.lineTo(150f, 100f)
        path.lineTo(50f, 100f)
        path.lineTo(150f, 200f)
//        path.lineTo(100, 50);
        //闭环
        path.close()
        //绘制路径方法
        canvas?.drawPath(path, paint)
        // < ======绘制路径

        //绘制弧线  ===== >
        val path1 = Path()
        val rect1 = RectF(200f, 50f, 350f, 200f)
        //弧线主要方法 oval 生成椭圆的矩形，startAngle 弧开始角度, sweepAngle 持续角度
        path1.arcTo(rect1, 180f, 180f, false)
        canvas?.drawPath(path1, paint)
        // < ===== 绘制弧线

//      起始点变成（X:50,Y:100）
        canvas?.translate(50f, 250f)
        //绘制区域 ==== >
        val region = Region(0, 0, 200, 50)
        val region1 = Region(150, -50, 300, 50)
        drawRegion(canvas, region, paint)
        drawRegion(canvas, region1, paint1)
        //区域操作
        // （1）Op.DIFFERENCE：显示region与region1不同区域
        // （2）Op.INTERSECT：显示region与region1相交区域
        // （3）Op.UNION：显示region与region1组合在一起区域
        // （4）Op.XOR：显示region与region1相交之外区域
        // （5）Op.REVERSE_DIFFERENCE：显示region1与region不同区域
        // （6）Op.REPLACE：显示region1区域
        region.op(region1, Region.Op.INTERSECT)
        paint1?.setColor(Color.GRAY)
        drawRegion(canvas, region, paint1)
        // < ==== 绘制区域

        //保存当前画布状态
        canvas?.save()
        //恢复到上一层保存的状态
//        canvas.restore();
    }

    fun drawRegion(canvas : Canvas?,region : Region,paint: Paint?){
        var iter = RegionIterator(region)
        var r = Rect()
        while (iter.next(r)){
            canvas?.drawRect(r,paint)
        }
    }

}