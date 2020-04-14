package aveek.com.management.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import aveek.com.management.R
import kotlin.math.cos
import kotlin.math.sin

private enum class FanSpeed(val label: Int) {
    OFF(R.string.off),
    LOW(R.string.low),
    MEDIUM(R.string.medium),
    HIGH(R.string.high)
}

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35


class DialView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    private var radiusOfTheCircle = 0.0f
    private var fanSpeed = FanSpeed.OFF
    private val pointPosition = PointF(0.0f, 0.0f)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radiusOfTheCircle = (minOf(w,h)/2.0*0.8).toFloat()
    }
    private fun PointF.computeXYForSpeed(pos : FanSpeed, radius : Float){
        val startAngle = Math.PI* (9/8.0)
        val angle = startAngle + pos.ordinal * (Math.PI/4)
        val x = (radius * cos(angle)).toFloat() + width /2
        val y = (radius * sin(angle)).toFloat() + height/2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = if(fanSpeed == FanSpeed.OFF) Color.GRAY else Color.GREEN
        canvas.drawCircle((width/2).toFloat(),(height/2).toFloat(),radiusOfTheCircle,paint)
        val markerRadius = radiusOfTheCircle + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed,markerRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radiusOfTheCircle/12, paint)
        val labelRadius = radiusOfTheCircle + RADIUS_OFFSET_LABEL
        for (i in FanSpeed.values()) {
            pointPosition.computeXYForSpeed(i, labelRadius)
            val label = resources.getString(i.label)
            canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
        }
    }
}

//class DialView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
//    private var radius = 0.0f
//    //    private var fanSpeed = FanSpeed.OFF
//    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
////        super.onSizeChanged(w, h, oldw, oldh)
//        radius = (min(w,h)/2.0*0.8).toFloat()
//    }
//    private fun PointF.computeXYForSpeed(pos: Int, radius: Float) {
//        // Angles are in radians.
//        val startAngle = Math.PI * (9 / 8.0)
//        val angle = startAngle + pos.ordinal * (Math.PI / 4)
//        x = (radius * cos(angle)).toFloat() + width / 2
//        y = (radius * sin(angle)).toFloat() + height / 2
//    }
//}