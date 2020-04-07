package aveek.com.management.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import aveek.com.management.R


/**
 * TODO: document your custom view class.
 * All computation are to the float and int
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ColorDialView @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0,
                                              defStyleRes: Int = 0) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var colors: ArrayList<Int> = arrayListOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.DKGRAY, Color.CYAN, Color.MAGENTA, Color.BLACK)
    private var dialDrawable: Drawable? = null
    private var dialDiameter = dptoDisplayPixels(100)
    private val paint : Paint = Paint().also {
        it.color = Color.BLUE
        it.isAntiAlias = true
    }

    // Pre computed Horizontal values
    private var horizontalSize = 0f
    private var verticalSize = 0f

    // Pre computed Vertical values
    private var centerHorizontal = 0f
    private var centerVertical = 0f
    private var tickPositionVertical = 0f

    private var extraPadding = dptoDisplayPixels(30)
    private var tickSize = dptoDisplayPixels(10).toFloat()
    private var angleBetweenColors = 0f

    // Pre computed padding values
    private var totalLeftPadding = 0f
    private var totalRightPadding = 0f
    private var totalTopPadding = 0f
    private var totalBottomPadding = 0f


    // automatically will get called when the view initialized
    init {
        // Take a drawable and customize it accordingly
        dialDrawable = context.getDrawable(R.drawable.ic_dial).also {
            it?.bounds = getCenteredBounds(dialDiameter)
            it?.setTint(Color.DKGRAY)
        }
        angleBetweenColors = 360f/ colors.size
        refreshValues()
    }

    // Method to compute, and call it when necessary
    // compute positions
    private fun refreshValues() {
        horizontalSize = paddingLeft+ dialDiameter.toFloat() + paddingRight + (extraPadding*2)
        verticalSize = paddingTop+ dialDiameter.toFloat() + paddingBottom + (extraPadding*2)

        centerHorizontal = totalLeftPadding+(horizontalSize - totalLeftPadding - totalRightPadding) / 2f
        centerVertical = totalTopPadding + (verticalSize -totalTopPadding - totalBottomPadding)/ 2f

        totalLeftPadding = (paddingLeft+extraPadding).toFloat()
        totalRightPadding = (paddingRight+extraPadding).toFloat()
        totalTopPadding = (paddingTop+extraPadding).toFloat()
        totalBottomPadding = (paddingBottom+extraPadding).toFloat()

        tickPositionVertical = paddingTop + extraPadding/2f


    }


    private fun getCenteredBounds(size: Int, scaler: Float = 1f): Rect {
        val half = ((if (size > 0) size / 2 else 1) * scaler).toInt()
        return Rect(-half, -half, half, half)
    }

    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        colors.forEachIndexed{i, color ->
            paint.color = colors[i]
            canvas.drawCircle(centerHorizontal,tickPositionVertical,tickSize,paint)
            canvas.rotate(angleBetweenColors,centerHorizontal,centerVertical)
        }
        canvas.restoreToCount(saveCount)
        canvas.translate(centerHorizontal, centerVertical)
        dialDrawable?.draw(canvas)

    }

    private fun dptoDisplayPixels(value: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context.resources.displayMetrics).toInt()
    }

}
