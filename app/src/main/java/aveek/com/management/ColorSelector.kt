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
    }
    private fun selectNextColor(){
        if (selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }
        else{
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }
    private fun selectPreviousColor(){
        if (selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }
        else{
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
    }

}