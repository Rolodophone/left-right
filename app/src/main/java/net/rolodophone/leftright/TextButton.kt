package net.rolodophone.leftright

import android.graphics.Paint
import android.graphics.RectF

class TextButton(val text: String, val align: Paint.Align, dim: RectF, activeState: State, onClick: () -> Unit) : Button(dim, activeState, {
    whitePaint.textAlign = align
    canvas.drawText(text, dim.left, dim.top, whitePaint)
}, onClick)