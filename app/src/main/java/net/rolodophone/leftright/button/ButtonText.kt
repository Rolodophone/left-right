package net.rolodophone.leftright.button

import android.graphics.Paint
import android.graphics.RectF
import net.rolodophone.leftright.main.*

class ButtonText(val text: String, val align: Paint.Align, ctx: MainActivity, state: State, dim: RectF, onClick: () -> Unit) : Button(ctx, state, dim, onClick) {

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