package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF

class ButtonBitmap(val bitmap: Bitmap, override val dim: RectF, override val onClick: () -> Unit) : Button() {
    override fun draw() {
        super.draw()
        canvas.drawBitmap(bitmap, null, dim, whitePaint)
    }
}