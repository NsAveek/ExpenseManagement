package aveek.com.management

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.color_selector.view.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ColorSelector @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {


    private var listOfColors = listOf(Color.BLACK,Color.BLUE,Color.RED,Color.GREEN)
    private var selectedColorIndex = 0

    init {

        val typedArray = context!!.obtainStyledAttributes(attrs,R.styleable.ColorSelector)
        val listOfColors = typedArray.getTextArray(R.styleable.ColorSelector_colors).map {
            Color.parseColor(it.toString())
        }
        typedArray.recycle() // typedArray must be recycled after each use

        orientation = LinearLayout.HORIZONTAL
        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector,this)

        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        colorSelectorArrowRight.setOnClickListener {
            selectNextColor()
        }
        colorSelectorArrowLeft.setOnClickListener {
            selectPreviousColor()
        }
        colorEnabled.setOnCheckedChangeListener{buttonView , isEnabled -> broadcastColor()}
    }
    private fun selectNextColor(){
        if (selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }
        else{
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()
    }
    private fun selectPreviousColor(){
        if (selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }
        else{
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()
    }

    fun setColorChangeListener (listener: ColorSelectedListener){
        this.colorSelectedListener = listener
    }
    private var colorSelectedListener : ColorSelectedListener? = null
    interface ColorSelectedListener {
        fun onColorSelected(color : Int)
    }

    private fun broadcastColor() {
        val color =  if (colorEnabled.isChecked) listOfColors[selectedColorIndex]
                            else Color.TRANSPARENT
        this.colorSelectedListener?.onColorSelected(color)
    }
}