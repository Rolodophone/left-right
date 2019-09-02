package net.rolodophone.leftright

import android.content.Context
import android.graphics.*

class Gui(context: Context) {
    companion object {
        val padding = !5
    }

    val game = Game()
    val status = Status(context)
    val paused = Paused()
    val gameOver = GameOver()


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
                //canvas.drawText("Player speed: ${player.ySpeed}", !2, !40, paint)
            }

            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, paint)
            paint.textSize = !22
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText(player.fuel.toInt().toString(), width - fuelW - !9, fuelH, paint)
        }
    }


    class Paused {
        var resume = Path()

        init {
            resume.moveTo(!87, halfHeight + !5)
            resume.lineTo(!87, halfHeight + !49)
            resume.lineTo(!120, halfHeight + !27)
            resume.close()
        }

        fun draw() {
            //draw paused icon
            canvas.drawRect(
                !90,
                halfHeight - !190,
                !150,
                halfHeight - !10,
                paint
            )
            canvas.drawRect(
                !210,
                halfHeight - !190,
                !270,
                halfHeight - !10,
                paint
            )

            //draw resume icon
            canvas.drawPath(resume, paint)
            paint.textSize = !40
            paint.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume", !132, halfHeight + !39, paint)
        }
    }


    class GameOver {
        fun draw() {

        }
    }
}
