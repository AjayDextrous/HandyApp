package com.makeathon.handyapp.ui.chat

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(context: Context, private val dividerDrawableResId: Int) : RecyclerView.ItemDecoration() {

    private val dividerDrawable: Drawable?
    private val padding = 8.toPixels(context)

    init {
        dividerDrawable = ContextCompat.getDrawable(context, dividerDrawableResId)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = dividerDrawable ?: return

        val left = parent.paddingLeft + padding
        val right = parent.width - parent.paddingRight - padding

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

    fun Int.toPixels(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).toInt()
    }
}
