package net.rolodophone.leftright

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.RectF

class Gui(context: Context) {
    companion object {
        val padding = !5
    }

    val game = Game()
    val status = Status(context)
    val paused = Paused()


    class Game {
        val pauseW = !45
        val pauseH = !45

        fun draw() {

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


    class Status(context: Context) {
        var fuelImg: Bitmap
        val fuelW = !22
        val fuelH = fuelW * (16f / 14f)
        val fuelDim = RectF(
            width - fuelW - padding,
            padding,
            width - padding,
            padding + fuelH
        )

        init {
            val opts = BitmapFactory.Options()
            opts.inScaled = false
            fuelImg = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, opts)
        }

        fun draw() {
            if (isDebug) {
                paint.textSize = !22
                paint.textAlign = Paint.Align.LEFT

                canvas.drawText(fps.toInt().toString(), !2, !20, paint)
                //canvas.drawText("Player speed: ${player.ySpeed}", !2, !20, paint)
            }

            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, paint)
            paint.textSize = !22
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(player.fuel.toInt().toString(), width - fuelW - !9, fuelH, paint)
        }
    }


    class Paused {
        val pauseW = halfWidth
        val pauseH = pauseW

        val resumeW = !45
        val resumeH = !45

        fun draw() {
            //draw paused icon
            canvas.drawRect(
                halfWidth - pauseW / 2f,
                height / 5f,
                halfWidth - pauseW / 6f,
                height / 5f + pauseH,
                paint
            )
            canvas.drawRect(
                halfWidth + pauseW / 6f,
                height / 5f,
                halfWidth + pauseW / 2f,
                height / 5f + pauseH,
                paint
            )

            //draw resume icon
//            canvas.drawRect(
//
//            )
        }
    }
}
