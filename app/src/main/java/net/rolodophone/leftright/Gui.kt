package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.RectF

class Gui(context: Context) {
    companion object {
        const val padding = 10f
    }


    val gameGui = GameGui(context)


    class GameGui(context: Context) {

        var fuelImg: Bitmap
        val fuelW = width / 16f
        val fuelH = fuelW * (16f / 14f)
        val fuelDim = RectF(
            width - fuelW - padding,
            padding,
            width - padding,
            padding + fuelH
        )

        val pauseW = width / 8f
        val pauseH = pauseW


        init {
            val opts = BitmapFactory.Options()
            opts.inScaled = false
            fuelImg = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, opts)
        }


        fun draw() {
            if (isDebug) {
                paint.textSize = 90f
                paint.textAlign = Paint.Align.LEFT

                canvas.drawText(fps.toInt().toString(), 10f, 100f, paint)
                canvas.drawText("Player speed: ${player.ySpeed}", 10f, 100f, paint)
            }

            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, paint)
            paint.textSize = width / 16f
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(player.fuel.toInt().toString(), width - fuelW - 30f, fuelH, paint)

            //draw pause button
            canvas.drawRect(
                padding,
                height - padding - pauseH,
                padding + pauseW / 3f,
                height - padding,
                paint
            )
            canvas.drawRect(
                padding + pauseW * 2f / 3f,
                height - padding - pauseH,
                padding + pauseW,
                height - padding,
                paint
            )
        }
    }
}
