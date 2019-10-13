package net.rolodophone.leftright

import android.graphics.Paint
import android.graphics.RectF

class ButtonText(val text: String, val align: Paint.Align, dim: RectF, condition: () -> Boolean, onClick: () -> Unit) : Button(dim, condition, {}, onClick) {
    private val x = when (align) {
        Paint.Align.LEFT -> dim.left
        Paint.Align.RIGHT -> dim.right
        else -> (dim.left + dim.right) / 2
    }

    init {
        draw = {
            whitePaint.textAlign = align
            canvas.drawText(text, x, dim.bottom, whitePaint)
        }
    }
}