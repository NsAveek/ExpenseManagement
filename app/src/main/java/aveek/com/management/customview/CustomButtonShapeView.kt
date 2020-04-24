package aveek.com.management.customview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import aveek.com.management.R

/**
 * A custom view class with rectangular shape .
 * with four types of cropping enabled
 * 1 = Top Left
 * 2 = Top Right
 * 3 = Bottom Left
 * 4 = Bottom Right
 *
 * Y increases going downwards
 * X increases going to the right
 */

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomButtonShapeView @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet,
                                                      defStyleAttr: Int = 0,
                                                      defStyleRes: Int = 0 ): View(context,attributeSet,defStyleAttr,defStyleRes){

    // region Member Variable
    private var rectWidth = 0
    private var rectHeight = 0
    private var rectShapeTopLeft : Rect? = null
    private var rectShapeTopRight : Rect? = null
    private var rectShapeBottomLeft : Rect? = null
    private var rectShapeBottomRight : Rect? = null

    private var ovalShapeTopLeft : RectF? = null
    private var ovalShapeTopRight : RectF? = null
    private var ovalShapeBottomLeft : RectF? = null
    private var ovalShapeBottomRight : RectF? = null

    private var innerOvalShape : RectF? = null
    private var paint = Paint()?.also {
        it.isAntiAlias = true // Smoothing Surface
    }
    private var totalWidth = 0
    private var totalHeight = 0

    private var extraPadding = dptoDisplayPixels(10)
    private var totalLeftPadding = 0
    private var totalRightPadding = 0
    private var totalTopPadding = 0
    private var totalBottomPadding = 0
    private var startAngle = 90f
    private var endAngle = 0f
    private var centerTop = 0f
    private var centerLeft = 0f
    private var centerRight = 0f
    private var centerBottom = 0f
    private var innerCenterTop = 0f
    private var innerCenterLeft = 0f
    private var innerCenterRight = 0f
    private var innerCenterBottom = 0f


    // endregion

    private var horizontalSize = 0f

    private var verticalSize = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomButtonShapeView)

        try {
            // Set color selected by the user
            paint.color = typedArray.getColor(R.styleable.CustomButtonShapeView_color,Color.RED) // Default color is RED

        } finally {
            typedArray.recycle() // recycle attributes for memory management
        }
        totalLeftPadding = extraPadding + paddingLeft
        totalRightPadding = extraPadding + paddingRight
        totalTopPadding = extraPadding + paddingTop
        totalBottomPadding = extraPadding + paddingBottom

        refreshValues()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        rectWidth = MeasureSpec.getSize(widthMeasureSpec)
        rectHeight = MeasureSpec.getSize(heightMeasureSpec)

        totalWidth =  paddingLeft + rectWidth + paddingRight
        totalHeight = paddingTop + rectHeight + paddingBottom

        refreshValues()

//        rectShape = Rect(-horizontalSize.toInt(),-verticalSize.toInt(), horizontalSize.toInt(), verticalSize.toInt())

//        rectShape = Rect(totalLeftPadding,totalTopPadding, rectWidth-totalRightPadding, rectHeight-totalBottomPadding)


        rectShapeTopLeft = Rect(totalLeftPadding,totalTopPadding, rectWidth/2-totalRightPadding, rectHeight/2-totalBottomPadding)
        rectShapeTopRight = Rect(totalLeftPadding+ rectShapeTopLeft!!.right,totalTopPadding, rectWidth-totalRightPadding, rectHeight/2-totalBottomPadding)
        rectShapeBottomLeft = Rect(totalLeftPadding,rectShapeTopLeft!!.bottom+ totalBottomPadding, rectShapeTopLeft!!.right, rectHeight-totalBottomPadding)
        rectShapeBottomRight = Rect(rectShapeTopRight!!.left,rectShapeBottomLeft!!.top, rectShapeTopRight!!.right, rectShapeBottomLeft!!.bottom)


//        ovalShapeTopLeft = RectF(centerLeft,centerTop,rectWidth/2f+ rectWidth/4f,rectHeight/2f+rectHeight/4f)
//
//        ovalShapeTopRight = RectF(rectWidth/2f,centerTop,rectWidth/2f - rectWidth/4f,rectHeight/2f+rectHeight/4f)

        ovalShapeBottomLeft = RectF(centerLeft,rectHeight/2f+rectHeight/4f,rectWidth/2f,rectHeight/2f)
//        ovalShapeBottomRight = RectF(centerLeft,centerTop,centerRight,centerBottom)




        innerOvalShape = RectF(innerCenterLeft,innerCenterTop,innerCenterRight,innerCenterBottom)
        // Left = Distance of the LEFT of the New View from the LEFT of the Parent View
        // Top =  Distance of the TOP of the New View from the TOP of the Parent View
        // Right = Distance of the RIGHT of the New View from the LEFT of the Parent View
        // Bottom = Distance of the BOTTOM of the New View from the TOP of the Parent View

        this.setMeasuredDimension(rectWidth,rectHeight)
    }

    override fun onDraw(canvas: Canvas) {


        drawRectangle(canvas,rectShapeTopLeft)
//        drawArc(canvas, ovalShapeTopLeft)
        drawRectangle(canvas,rectShapeTopRight)
//        drawArc(canvas,ovalShapeTopRight)
        drawRectangle(canvas,rectShapeBottomLeft)
//        drawArc(canvas,ovalShapeBottomLeft)
        drawRectangle(canvas,rectShapeBottomRight)
//        drawArc(canvas,ovalShapeBottomRight)


//        drawOvalTopLeft(canvas, ovalShapeTopLeft)
//        drawInnerOval(canvas)
//        drawCircle(canvas)
        canvas.save()
    }

    private fun drawInnerOval(canvas: Canvas) {
        val ovalPaint = Paint().also {
            it.color = Color.YELLOW
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }
        canvas.drawOval(innerOvalShape,ovalPaint)
    }

    private fun drawOvalTopLeft(canvas: Canvas, ovalShapeTopLeft : RectF?) {
        val ovalPaint = Paint().also {
            it.color = Color.WHITE
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }
        canvas.drawOval(ovalShapeTopLeft,ovalPaint)
    }

    private fun drawRectangle(canvas: Canvas, rectShape: Rect?) {
//        canvas.drawRect(rectShape,paint)
        canvas.drawRect(rectShape,paint)
        invalidate()
    }

    private fun drawCircle(canvas: Canvas) {
        val circlePaint = Paint().also {
            it.color = Color.YELLOW
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }
        canvas.drawCircle(rectWidth.toFloat(),rectHeight.toFloat(),100f,circlePaint)
    }

    private fun drawArc(canvas: Canvas, ovalShape: RectF?) {

        val ovalPaint = Paint().also {
            it.color = Color.WHITE
            it.style = Paint.Style.FILL
            it.isAntiAlias = true
        }

        when(ovalShape){
            ovalShapeTopLeft -> {
                startAngle = 180f
                endAngle = 270f
            }
            ovalShapeTopRight -> {
                startAngle = 270f
                endAngle = 0f
            }
            ovalShapeBottomLeft -> {
                startAngle = 90f
                endAngle = 180f
            }
            ovalShapeBottomRight -> {
                startAngle = 90f
                endAngle = 0f
            }
        }

        var sweepAngle = endAngle-startAngle

        if (sweepAngle <0){
            sweepAngle += 360
        }

        // Both way works
        canvas.drawArc(ovalShape, startAngle, sweepAngle, true,ovalPaint )
//        val arcPath : Path = Path().also {
//            it.arcTo(ovalShape,startAngle,sweepAngle)
//
//        }
//        canvas.drawPath(arcPath,ovalPaint)
        invalidate()
    }

    private fun refreshValues(){
        centerLeft = rectWidth/4f
        centerTop = rectHeight/4f
        centerRight = rectWidth/4f + rectWidth/2f
        centerBottom = rectHeight/4f+rectHeight/2f

        // For Oval shape the calculation is with 2:3 ratio
        innerCenterLeft = centerLeft + rectWidth/88f
        innerCenterTop = centerTop + rectHeight/132f
        innerCenterRight = centerRight - rectWidth/88f
        innerCenterBottom = centerBottom - rectHeight/132f

        horizontalSize = (totalLeftPadding + rectWidth + totalRightPadding).toFloat()
        verticalSize = (totalTopPadding + rectHeight + totalBottomPadding).toFloat()
    }

    private fun dptoDisplayPixels(value: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics).toInt()
    }
}
