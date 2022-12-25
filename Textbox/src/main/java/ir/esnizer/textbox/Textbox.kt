package ir.esnizer.textbox

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import ir.esnizer.textbox.databinding.LayoutTextboxBinding

class Textbox : FrameLayout {

    private var mTitle: String = ""
    private var mSentence: String = ""
    private var mColor: Int = 0
    private var mBackColor: Int = 0
    private var mSize: Int = 16
    private var fixedY = 0

    constructor(context: Context) : super(context) {
        initor(context, null, 0)
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        initor(context, attr, 0)
    }

    constructor(context: Context, attr: AttributeSet, def: Int) : super(context, attr, def) {
        initor(context, attr, def)
    }

    private fun initor(context: Context, attr: AttributeSet?, def: Int) {
        val t = context.obtainStyledAttributes(attr, R.styleable.Textbox, def, 0)
        try {
            t.let {

                if (t.hasValue(R.styleable.Textbox_textbox_title)) mTitle = it.getString(R.styleable.Textbox_textbox_title)!!.toString()
                if (t.hasValue(R.styleable.Textbox_textbox_sentence)) mSentence = it.getString(R.styleable.Textbox_textbox_sentence)!!.toString()



                mColor = it.getColor(R.styleable.Textbox_textbox_colors, Color.BLACK)
                mBackColor = it.getColor(R.styleable.Textbox_textbox_backcolor, Color.RED)

                mSize = it.getDimensionPixelSize(R.styleable.Textbox_textbox_sizes, 16)
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }

        this.vif = LayoutTextboxBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_textbox, CardView(context), false))
        addView(vif.Cardd)
        Log.w("eerf0", fixedY.toString())
        fixedY = if (vif.subContainer.measuredHeight > 0) vif.subContainer.measuredHeight else fixedY

        if (mTitle.isEmpty()) vif.tvTitleTextbox.text = "isit"

        //layoutParams = vif.root.layoutParams
        isShow = vif.subContainer.height > 0
        onListens()

        onSets()

        t.recycle()

    }

    private fun onSets() {
        vif.apply {
            tvTitleTextbox.text = mTitle
            tvTextContainerTextbox.text = mSentence
            root.setCardBackgroundColor(mBackColor)
            tvTextContainerTextbox.setTextColor(mColor)
            tvTitleTextbox.setTextColor(mColor)
            requestLayout()
        }
    }


    fun String.isConNull(): String {

        return this.ifEmpty { "" }

    }

    fun setInflate(vif: LayoutTextboxBinding) = run {
        this.vif = vif
    }

    private lateinit var vif: LayoutTextboxBinding
    private var isShow = false
    private var isAnimation = true

    private fun onListens() {
        vif.imgArrowsTextbox.setOnClickListener {
            Log.w("eerf0", fixedY.toString())
            fixedY = if (vif.subContainer.measuredHeight > 0) vif.subContainer.measuredHeight else fixedY
            Log.w("eerf", fixedY.toString())

            if (isAnimation) {
                if (isShow) {val anim1 = ValueAnimator.ofInt(0, fixedY)
                    anim1.duration = 500
                    anim1.addUpdateListener {

                        val int = it.animatedValue as Integer

                        vif.subContainer.layoutParams.height = int.toInt()
                        vif.subContainer.requestLayout()

                    }
                    anim1.start()
//                    vif.subContainer.setLayoutParams(LinearLayout.LayoutParams(vif.subContainer.width, LinearLayout.LayoutParams.WRAP_CONTENT))
                    Log.w("eerf", vif.subContainer.layoutParams.height.toString())
                    vif.imgArrowsTextbox.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)

                } else {


                    val anim1 = ValueAnimator.ofInt(fixedY, 0)
                    anim1.duration = 500
                    anim1.addUpdateListener {

                        val int = it.animatedValue as Integer

                        vif.subContainer.layoutParams.height = int.toInt()
                        vif.subContainer.requestLayout()
                    }
                    anim1.start()
//                    vif.subContainer.setLayoutParams(LinearLayout.LayoutParams(vif.subContainer.width, 0))
                    vif.imgArrowsTextbox.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                }
            } else {
//                var from = 0
//                var to = 1
//                if (isShow) {
//                    from = fixedY
//                    to = 1
//                }else {
//                    from = 1
//                    to = fixedY
//                }
//                val anim = ValueAnimator.ofInt(from, to)
//                anim.addUpdateListener {
//                    val heghi = MeasureSpec.makeMeasureSpec(it.animatedValue as Int, MeasureSpec.AT_MOST)
//                    //vif.subContainer.measure(vif.subContainer.measuredWidth, 10000)
//                }
//                anim.duration = 1000
//                anim.start()
            }
            isShow = !isShow

        }
    }

    fun setTitle(title: String) = vif.tvTitleTextbox.setText(title)

    fun setSentence(text: String) = vif.tvTextContainerTextbox.setText(text)

    fun addOnContainerChangeListener(listen: (LinearLayout, Boolean) -> Boolean) {

        isAnimation = (listen.invoke(vif.subContainer, isShow))

    }


}