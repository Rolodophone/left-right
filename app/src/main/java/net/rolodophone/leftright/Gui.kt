package net.rolodophone.leftright

import android.content.Context
import android.graphics.*
import android.os.SystemClock
import kotlin.random.Random

class Gui(context: Context) {
    companion object {
        val padding = w(5)
    }

    val game = Game()
    val debug = Debug()
    val status = Status(context)
    val paused = Paused()
    val gameOver = GameOver(context)


    class Game {
        val pauseW = w(45)
        val pauseH = w(45)

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


    class Debug {
        var prevTime = SystemClock.elapsedRealtime()
        var viewFps = fps.toInt()

        fun draw() {
            //draw fps
            if (SystemClock.elapsedRealtime() > prevTime + 500) {
                viewFps = fps.toInt()
                prevTime = SystemClock.elapsedRealtime()
            }
            pWhite.textAlign = Paint.Align.LEFT
            canvas.drawText("FPS: $viewFps", padding, w(50) + padding + statusBarHeight, pWhite)
        }
    }


    class Status(context: Context) {
        var fuelImg: Bitmap
        val fuelW = w(22)
        val fuelH = fuelW * (16f / 14f)
        val fuelDim = RectF(
            width - fuelW - padding,
            padding + statusBarHeight,
            width - padding,
            padding + fuelH + statusBarHeight
        )

        init {
            val opts = BitmapFactory.Options()
            opts.inScaled = false
            fuelImg = BitmapFactory.decodeResource(context.resources, R.drawable.fuel, opts)
        }

        fun draw() {
            pWhite.textSize = w(22)


            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, pWhite)

            pWhite.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                player.fuel.toInt().toString(),
                width - fuelW - w(9),
                w(25) + statusBarHeight,
                pWhite
            )

            //draw distance
            pWhite.textAlign = Paint.Align.LEFT
            canvas.drawText(
                player.distance.toInt().toString() + "m",
                w(7),
                w(25) + statusBarHeight,
                pWhite
            )
        }
    }


    class Paused {
        var resume = Path()

        init {
            resume.moveTo(w(87), halfHeight + w(5))
            resume.lineTo(w(87), halfHeight + w(49))
            resume.lineTo(w(120), halfHeight + w(27))
            resume.close()
        }

        fun draw() {
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, pDimmer)

            //draw paused icon
            canvas.drawRect(
                w(90),
                halfHeight - w(190),
                w(150),
                halfHeight - w(10),
                pWhite
            )
            canvas.drawRect(
                w(210),
                halfHeight - w(190),
                w(270),
                halfHeight - w(10),
                pWhite
            )

            //draw resume icon
            canvas.drawPath(resume, pWhite)
            pWhite.textSize = w(40)
            pWhite.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume", w(132), halfHeight + w(39), pWhite)
        }
    }


    class GameOver(context: Context) {
        val deathMsgPaint = Paint()

        var alpha = 0f
        var rotation = 0f
        var maxRotation = 0f

        var deathMsgImg: Bitmap
        val deathMsgDim = RectF(
            w(30),
            h(80),
            w(330),
            h(80) + w(72.972972973f)
        )

        init {
            val opts = BitmapFactory.Options()
            opts.inScaled = false
            deathMsgImg =
                BitmapFactory.decodeResource(context.resources, R.drawable.death_msg, opts)
        }


        fun setup() {
            alpha = 0f
            rotation = 0f
            maxRotation = -35f + Random.nextFloat() * (70f)
        }


        fun draw() {
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, pDimmer)


            alpha += 25f / fps
            rotation += (maxRotation / 4f) / fps
            if (alpha > 100f) alpha = 100f
            if (rotation > maxRotation) rotation = maxRotation

            canvas.drawBitmap(deathMsgImg, null, deathMsgDim, deathMsgPaint)
        }
    }
}
