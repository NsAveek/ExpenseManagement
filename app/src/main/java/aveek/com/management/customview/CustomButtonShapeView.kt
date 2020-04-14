package aveek.com.management.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.AttrRes
import android.support.annotation.RequiresApi
import android.support.annotation.StyleRes
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import aveek.com.management.R

/**
 * A custom view class with rectangular shape .
 * with four types of cropping enabled
 * 1 = Top Left
 * 2 = Top Right
 * 3 = Bottom Left
 * 4 = Bottom Right
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomButtonShapeView @JvmOverloads constructor(context: Context,
                                                      attributeSet: AttributeSet,
                                                      defStyleAttr: Int = 0,
                                                      defStyleRes: Int = 0 ): View(context,attributeSet,defStyleAttr,defStyleRes){

    // region Member Variable
    private var rectWidth = 0
    private var rectHeight = 0
    private var rectShape : Rect? = null
    private var paint = Paint()?.also {
        it.isAntiAlias = true // Smoothing Surface
    }
    private var totalWidth = 0
    private var totalHeight = 0
    // endregion

    // TODO : 1. Take the screen width
    // TODO : 2. Take the screen height
    // TODO : 3. screen width/2 = rect width
    // TODO : 4. screen height/2 = rect height

    // TODO : 5. set color selected by the developer in XML

    // TODO : 6. screen height/2 = rect height
    // TODO : 7. screen height/2 = rect height
    // TODO : 8. screen height/2 = rect height
    // TODO : 9. screen height/2 = rect height

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomButtonShapeView)
        try {
            // Set color selected by the user
            paint.color = typedArray.getColor(R.styleable.CustomButtonShapeView_color,Color.RED)
        } finally {
            typedArray.recycle() // recycle attributes for memory management
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        rectWidth = MeasureSpec.getSize(widthMeasureSpec)/2
        rectHeight = MeasureSpec.getSize(heightMeasureSpec)/2

        totalWidth = paddingLeft + rectWidth + paddingRight
        totalHeight = paddingTop + rectHeight + paddingBottom

        rectShape = Rect(-totalWidth,-totalHeight, totalWidth, totalHeight)

        this.setMeasuredDimension(rectWidth,rectHeight)
    }
    override fun onDraw(canvas: Canvas) {

        canvas.drawRect(rectShape,paint)
        canvas.save()

//        super.onDraw(canvas)
//
//        // TODO: consider storing these as member variables to reduce
//        // allocations per draw cycle.
//        val paddingLeft = paddingLeft
//        val paddingTop = paddingTop
//        val paddingRight = paddingRight
//        val paddingBottom = paddingBottom
//
//        val contentWidth = width - paddingLeft - paddingRight
//        val contentHeight = height - paddingTop - paddingBottom
//
//        exampleString?.let {
//            // Draw the text.
//            canvas.drawText(it,
//                    paddingLeft + (contentWidth - textWidth) / 2,
//                    paddingTop + (contentHeight + textHeight) / 2,
//                    textPaint)
//        }
//
//        // Draw the example drawable on top of the text.
//        exampleDrawable?.let {
//            it.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight)
//            it.draw(canvas)
//        }
    }


//    private fun init(attrs: AttributeSet?, defStyle: Int) {
//        // Load attributes
//        val a = context.obtainStyledAttributes(
//                attrs, R.styleable.CustomButtonShapeView, defStyle, 0)
//
//        _exampleString = a.getString(
//                R.styleable.CustomButtonShapeView_exampleString)
//        _exampleColor = a.getColor(
//                R.styleable.CustomButtonShapeView_exampleColor,
//                exampleColor)
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        _exampleDimension = a.getDimension(
//                R.styleable.CustomButtonShapeView_exampleDimension,
//                exampleDimension)
//
//        if (a.hasValue(R.styleable.CustomButtonShapeView_exampleDrawable)) {
//            exampleDrawable = a.getDrawable(
//                    R.styleable.CustomButtonShapeView_exampleDrawable)
//            exampleDrawable?.callback = this
//        }
//
//        a.recycle()
//
//        // Set up a default TextPaint object
//        textPaint = TextPaint().apply {
//            flags = Paint.ANTI_ALIAS_FLAG
//            textAlign = Paint.Align.LEFT
//        }
//
//        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements()
//    }

//    private fun invalidateTextPaintAndMeasurements() {
//        textPaint?.let {
//            it.textSize = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
//    }


}
