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
                pWhite
            )
            canvas.drawRect(
                padding + pauseW * 2f / 3f,
                height - padding - pauseH,
                padding + pauseW,
                height - padding,
                pWhite
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
            pWhite.textSize = !22


            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, pWhite)

            pWhite.textAlign = Paint.Align.RIGHT
            canvas.drawText(player.fuel.toInt().toString(), width - fuelW - !9, !25, pWhite)

            //draw distance
            pWhite.textAlign = Paint.Align.LEFT
            canvas.drawText(player.distance.toInt().toString() + "m", !7, !25, pWhite)
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
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, pDimmer)

            //draw paused icon
            canvas.drawRect(
                !90,
                halfHeight - !190,
                !150,
                halfHeight - !10,
                pWhite
            )
            canvas.drawRect(
                !210,
                halfHeight - !190,
                !270,
                halfHeight - !10,
                pWhite
            )

            //draw resume icon
            canvas.drawPath(resume, pWhite)
            pWhite.textSize = !40
            pWhite.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume", !132, halfHeight + !39, pWhite)
        }
    }


    class GameOver {
        fun draw() {
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, pDimmer)


        }
    }
}
