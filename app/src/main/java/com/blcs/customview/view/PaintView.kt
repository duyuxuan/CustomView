package com.blcs.kotlinstudy.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PaintView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var paint: Paint?
    var can:Canvas? = null
    var createBitmap:Bitmap? = null
    init {
//        关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        paint = Paint()
        paint?.color = Color.WHITE
        paint?.style = Paint.Style.FILL_AND_STROKE
        paint?.textSize = 64f
        createCanvas()
    }

    fun createCanvas() {
        //新建一个空白 bitmap
        createBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        //从图片中加载
//        BitmapFactory.decodeResource (getResources(), R.drawable.ic_launcher_background, null)

        //创建画布
        can = Canvas(createBitmap)
        can?.drawColor(Color.WHITE)
//        Canvas().setBitmap(bitmap);

        /**
         * 保存指定矩形区域的Canvas内容
         * RectF bounds:要保存的区域所对应的矩形对象。
         * int saveFlags:标识 表示一些保存样式。
         */
//        public int saveLayer(RectF bounds,Paint paint,int saveFlags)
//        public int saveLayer(float left,float top,float right,float bottom,Paint paint,int saveFlags)

//        恢复画布
//        restore()与restoreToCount()。
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.BLACK)

//        绘制文字 常用函数
        drawText(canvas)
//        绘制贝济埃曲线
        drawBezieCurve(canvas)
//        阴影/发光效果与图片阴影/Shader着色器
        drawShadow(canvas)
//        混合模式
        drawXfermode(canvas)
//        在新的画布上绘图
        setCanvas(canvas)
    }
    /**
     *  在新的画布上绘图
     */
    fun setCanvas(canvas : Canvas?){
        paint?.setXfermode(null)
        paint?.color = Color.GRAY
        can?.drawText("我是谁",can?.width!!/3.toFloat(),can?.height!!/5.toFloat(),paint)
        can?.drawText("我在哪",can?.width!!/3.toFloat(),can?.height!!/3.toFloat(),paint)
        can?.drawText("我在干什么",can?.width!!/3.toFloat(),can?.height!!/2.toFloat(),paint)
//        绑定到View布局中
        canvas?.drawBitmap(createBitmap,canvas.width/4.toFloat(),canvas.height/2.toFloat(),paint)
    }

    /**
     *  混合模式
     *  混合模式是通过Paint类中的Xfermode setXfermode(Xfermode xfermode)函数实现的，
     *  它的参数Xfermode是一个空类，主要靠它的子类来实现不同的功能。派生自Xfermode的子类有AvoidXfermode、PixelXorXfermode和 PorterDuffXfermode。
     *  注：AvoidXfermode、PixelXorXfermode不支持硬件加速，而PorterDuffXfermode是部分不支硬件加速。
     */
    private fun drawXfermode(canvas: Canvas?) {
        paint?.setMaskFilter(null)

//        使用Xfermode时，先做两件事 禁用硬件加速 / 使用离屏绘制
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        /**
         *  PorterDuffXfermode
         *  只有一个参数PorterDuff.Mode,表示混合模式，枚举值有18个，表示各种混合模式。每种模式都对应着一种算法。
         *  PorterDuffXfermode之源图像模式
         * （1）Mode.SRC：在处理源图像所在区域的相交问题时，全部以源图像显示。
         * （2）Mode.SRC_IN：在相交时利用目标图像的透明度来改变源图像的透明度和饱和度。
         * （3）Mode.SRC_OUT：当目标图像有图像时结果显示空白像素，当目标图像没有图像时结果显示源图像。
         * （4）Mode.SRC_OVER：当源图像的透明度为100%时，原样显示源图像。
         * （5）Mode.SRC_ATOP：当透明度是100%和0时，SRC_ATOP和SRC_IN模式是通用的。当透明度不是100%和0时，SRC_ATOP相比SRC_IN源图像的饱和度会增加，显得更亮。
         */
        paint?.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_OUT))
        var rect = Rect(100,500,300,700)
        canvas?.drawRect(rect,paint)
        paint?.color = Color.RED
        var rect1 = Rect(200,600,400,800)
        canvas?.drawRect(rect1,paint)
    }

    /**
     * 阴影/发光效果与图片阴影/Shader着色器
     */
    private fun drawShadow(canvas: Canvas?) {
        paint?.style = Paint.Style.FILL_AND_STROKE
        paint?.color = Color.WHITE
//        =============阴影===================
        /**
         * 设置阴影
         * float radius：模糊半径，radius越大越模糊、越小越清晰。如果radius设置为0，则阴影消失* 不见
         * float dx：阴影的横向偏移距离，正值像右偏移，负值向左偏移。
         * float dy：阴影的纵向偏移距离，正值向下偏移，负值向上偏移。
         * int color：绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）。
         * 注意：setShadowLayer()函数只有文字绘制阴影支持硬件加速，其他都不支持硬件加速。
         * 所以，为了方便起见，需要在自定义控件中禁用硬件加速。
         */
        paint?.setShadowLayer(10f, 10f, 10f, Color.GRAY)

        canvas?.drawCircle(canvas?.width!! / 2f + 200, canvas?.height!! / 10f, 50f, paint)
//        清除阴影
        paint?.clearShadowLayer()
//          绘制文字要开始硬件加速才有效果
//        canvas?.drawText("我是谁", canvas?.width / 2f, canvas?.height / 2f, paint)
//        <TextView
//        ....
//        android:shadowRadius = "3"
//        android:shadowDx = "5"
//        android:shadowDy = "5"
//        android:shadowColor = "@android:color/red"/>
//        代码中添加
//        tv.setShadowLayer(2,5,5,Color.GRAY)
//        =============发光效果与图片阴影===================
        /**
         * 发光效果与图片阴影
         * float radius：用来定义模糊半径，同样采用高斯模糊算法
         * Blur style：发光样式，有Blur.INNER内发光、Blur.SOLID外发光、
         * Blur.NORMAL内外发光、Blur.OUTER 仅显示发光效果
         */
        val blurMaskFilter = BlurMaskFilter(50f, BlurMaskFilter.Blur.SOLID)

        /**
         * setMaskFilter()函数中的MaskFilter是没有具体实现的，是通过派生子类来实现具体的不同
         * 功能的。MaskFilter有两个派生类：BlurMaskFilter和EmbossMaskFilter。其中，
         * BlurMaskFilter能够实现发光效果；而EmbossMaskFilter则可以用于实现浮雕效果。
         */
        paint?.setMaskFilter(blurMaskFilter)

//        =============Shader===================
        /**
         * BitmapShader构造函数
         * 相当于Photoshop中的印章工具，bitmap用来指定图案，tileX用来指定当X轴超出单张图片大
         * 小时所使用的重复策略，tileY用来指定当Y轴超出单张图片大小时所使用的重复策略。
         * TileMode的取值如下：
         * TileMode.CLAMP：用边缘色彩来填充多余空间
         * TileMode.REPEAT：重复原图像来填充多余空间
         * TileMode.MIRROR: 重复使用镜像模式的图像来填充多余空间。
         */
//        public BitmapShader(Bitmap bitmap,TileMode tileX,TileMode tileY)

        /**
         * 这个构造函数只能指定两种颜色之间的渐变。
         * 参数中的（x0,y0）就是起始渐变点坐标，（x1,y1）就是结束渐变点坐标
         * color0就是起始颜色，color1就是终止颜色。
         * TileMode tile:与BitmapShader一样。
         */
//        public LinearGradient(float x0,float y0,float x1,float y1,int color0,int color1,TileMode tile)

        /**
         * 这个构造函数可以实现多种颜色之间的渐变
         * 参数中的（x0,y0）就是起始渐变点坐标，（x1,y1）就是结束渐变点坐标
         * color[]用于指定渐变的颜色值数组
         * positions[]与渐变的颜色相对应，取值是0~1的Float类型数据，表示每种颜色在整条渐变
         * 线中的百分比位置
         */
//        public LinearGradient(float x0,float y0,float x1,float y1,int colors[],float positions[],TileMode tile)
        /**
         * 着色器
         */
//        paint?.setShader()

//        关闭硬件加速才有效果
        canvas?.drawCircle(canvas?.width!! / 2f, canvas?.height!! / 10f, 50f, paint)
    }

    /**
     * 绘制贝济埃曲线
     */
    private fun drawBezieCurve(canvas: Canvas?) {
        paint?.style =Paint.Style.STROKE
        paint?.color = Color.WHITE
//          二阶贝济埃曲线
//        public void quadTo(float x1,float y1,float x2,float y2)
//        public void rQuadTo(float dx1,float dy1,float dx2,float dy2)
//          三阶贝济埃曲线
//        public void cubicTo(float x1,float y1,float x2,float y2,float x3,float y3)
//        public void rCubicTo(float x1,float y1,float x2,float y2,float x3,float y3)
        val path = Path()
        path.moveTo(0f, 200f)
        path.quadTo(0f, 200f, 100f, 300f)
        path.quadTo(200f, 400f, 300f, 300f)
        path.quadTo(400f, 200f, 500f, 300f)
        path.quadTo(600f, 400f, 700f, 300f)
        path.quadTo(800f, 200f, 900f, 300f)
        path.quadTo(1000f, 400f, 1100f, 300f)
        canvas?.drawPath(path, paint)
    }

    /**
     * 绘制文字 常用函数
     */
    private fun drawText(canvas: Canvas?) {
//        paint常用函数
//        reset()：重置画笔
//        setColor(int color)：给画笔设置颜色值
//        setARGB()：给画笔设置颜色值
//        setAlpha(int a)：设置画笔透明度
//        setStyle(Paint.Style style)：设置画笔样式
//        setStrokeWidth(float width)：设置画笔宽度
//        setStrokeMiter(float miter)： 设置画笔的倾斜度
//        setAntiAlias(boolean aa)：设置画笔是否抗锯齿
//        setPathEffect(PathEffect effect)：设置路径样式
//        setStrokeCap(Paint.Cap cap)：设置线帽样式
//        setTextSize（float textSize）：设置文字大小
//        setFakeBoldText(boolean fakeBoldText)：设置是否为粗体文字
//        setStrikeThruText(boolean strikeThruText)：设置带有删除线效果
//        setUnderlineText(boolean underlineText)：设置下划线

//        Paint.Align.LEFT：从原点x.y左侧开始绘制
//        Paint.Align.CENTER：使原点x.y正好在所要绘制矩形的正中间
//        Paint.Align.RIGHT：从原点x.y右侧开始绘制
//        setTextAlign(Paint.Align align)：设置开始绘图点位置

//        setTextScaleX(float scaleX)：设置水平拉伸
//        setTextSkewX(float skewX)：设置字体水平倾斜度
//        setTypeface(Typeface typeface)：设置字体样式
//        setLinearText(boolean linearText)：设置是否打开线性文本标识

        /**
         * text：要绘制的文字
         * x：绘制原点x坐标
         * y：绘制原点y坐标
         * paint：用来作画的画笔
         */
        val centerX = canvas?.width!! / 10f
        val centerY = canvas?.height!! / 10f
        canvas?.drawText("我是谁", centerX, centerY, paint)
        // 系统在绘制文字时还有4条线，分别是ascent、descent、top和bottom。基线的位置是在构造drawText()函数时由参数y来决定的。而这4条线是由FontMetrics计算出来的
        // 获取FontMetrics对象
//        val fm = paint?.fontMetrics
//        val top = centerY + fm?.top!!.toInt()
//        val bottom = centerY + fm?.bottom!!.toInt()
//        //所占区域的高度
//        val height = bottom - top
//        Log.e("height", "==" + height)
//        //所占区域的高度
//        val width = paint?.measureText("我是谁")
//        Log.e("width", "==" + width)
        //获取指定字符串所对应的最小矩形，以（0.0）点所在位置为基线
//        paint?.getTextBounds("我是谁", 0, 0, rect)
//        绘制起点
        paint?.color = Color.RED
        canvas?.drawPoint(centerX.toFloat(), centerY.toFloat(), paint)
    }
}