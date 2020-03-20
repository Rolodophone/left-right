package net.rolodophone.leftright.button

import android.graphics.Paint
import android.graphics.RectF
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.w
import net.rolodophone.leftright.main.whitePaint

class ButtonText(val text: String, val align: Paint.Align, override val dim: RectF, override val onClick: () -> Unit) : Button() {

    private val x = when (align) {
        Paint.Align.LEFT -> dim.left
        Paint.Align.RIGHT -> dim.right
        Paint.Align.CENTER -> (dim.left + dim.right) / 2
    }
    private val textSize = dim.height() - w(3)

    override fun draw() {
        super.draw()

        whitePaint.textAlign = align
        whitePaint.textSize = textSize
        canvas.drawText(text, x, dim.bottom, whitePaint)
    }
}