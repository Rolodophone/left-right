package net.rolodophone.leftright.button

import android.graphics.Bitmap
import android.graphics.RectF
import net.rolodophone.leftright.main.MainView
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.main.whitePaint

class ButtonBitmap(val bitmap: Bitmap, override val c: MainView.c, override val dim: RectF, override val onClick: () -> Unit) : Button(c, dim, onClick) {
    override fun draw() {
        super.draw()
        canvas.drawBitmap(bitmap, null, dim, whitePaint)
    }
}