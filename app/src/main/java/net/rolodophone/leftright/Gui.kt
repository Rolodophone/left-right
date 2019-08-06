package net.rolodophone.leftright

import android.content.Context

class Gui(context: Context) : GameObject {
    override fun update() {}
    override fun draw() {
        paint.textSize = 90f
        if (isDebug) canvas.drawText(fps.toInt().toString(), 10f, 100f, paint)
    }
}