package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.RectF

class Gui(context: Context) : GameObject {
    var fuelImg: Bitmap
    val fuelPadding = 10f
    val fuelW = width / 16f
    val fuelH = fuelW * (16f / 14f)
    val fuelDim = RectF(
        width - fuelW - fuelPadding,
        fuelPadding,
        width - fuelPadding,
        fuelPadding + fuelH
    )

    init {
        val opts = BitmapFactory.Options()
        opts.inScaled = false
        fuelImg = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, opts)
    }


    override fun update() {}


    override fun draw() {
        if (isDebug) {
            paint.textSize = 90f
            paint.textAlign = Paint.Align.LEFT

            canvas.drawText(fps.toInt().toString(), 10f, 100f, paint)
            //if (player.goingL) canvas.drawText("Going left", 10f, 200f, paint)
            //if (player.goingR) canvas.drawText("Going right", 10f, 300f, paint)
        }


        canvas.drawBitmap(fuelImg, null, fuelDim, paint)
        paint.textSize = width / 16f
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText(player.fuel.toInt().toString(), width - fuelW - 30f, fuelH, paint)
    }
}