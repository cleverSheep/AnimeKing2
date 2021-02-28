package com.murrayde.animekingandroid.epoxy.models

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.murrayde.animekingandroid.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val textView: TextView

    init {
        inflate(context, R.layout.header_item_view, this)
        orientation = VERTICAL
        textView = (findViewById(R.id.anime_header))
        textView.compoundDrawablePadding = (4 * resources.displayMetrics.density).toInt()
    }

    @TextProp
    lateinit var title: CharSequence

    @AfterPropsSet
    fun useProps() {
        textView.text = title
    }
}