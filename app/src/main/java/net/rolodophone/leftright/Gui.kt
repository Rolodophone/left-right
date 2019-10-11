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
                whitePaint
            )
            canvas.drawRect(
                padding + pauseW * 2f / 3f,
                height - padding - pauseH,
                padding + pauseW,
                height - padding,
                whitePaint
            )
        }
    }


    class Debug {
        val showFps = true
        val showGrid = true

        val gridPaint = Paint()

        var prevTime = SystemClock.elapsedRealtime()
        var viewFps = fps.toInt()

        init {
            gridPaint.color = Color.argb(100, 255, 255, 255)
        }

        fun draw() {
            //draw fps
            if (showFps) {
                if (SystemClock.elapsedRealtime() > prevTime + 500) {
                    viewFps = fps.toInt()
                    prevTime = SystemClock.elapsedRealtime()
                }
                whitePaint.textAlign = Paint.Align.LEFT
                canvas.drawText("FPS: $viewFps", padding, w(50) + padding + statusBarHeight, whitePaint)
            }

            //draw grid
            if (showGrid) {

                var x = w(20)
                while (x <= w(340)) {
                    canvas.drawLine(x, 0f, x, height, gridPaint)
                    x += w(20)
                }

                x = w(120)
                while (x <= w(240)) {
                    canvas.drawLine(x, 0f, x, height, whitePaint)
                    x += w(120)
                }

                var y = h(20)
                while (y <= h(340.001f)) {
                    canvas.drawLine(0f, y, width, y, gridPaint)
                    y += h(20)
                }

                y = h(120)
                while (y <= h(240)) {
                    canvas.drawLine(0f, y, width, y, whitePaint)
                    y += h(120)
                }
            }
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
            whitePaint.textSize = w(22)


            //draw fuel meter
            canvas.drawBitmap(fuelImg, null, fuelDim, whitePaint)

            whitePaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                player.fuel.toInt().toString(),
                width - fuelW - w(9),
                w(25) + statusBarHeight,
                whitePaint
            )

            //draw distance
            whitePaint.textAlign = Paint.Align.LEFT
            canvas.drawText(
                player.distance.toInt().toString() + "m",
                w(7),
                w(25) + statusBarHeight,
                whitePaint
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
            canvas.drawRect(0f, 0f, width, height, dimmerPaint)

            //draw paused icon
            canvas.drawRect(
                w(90),
                halfHeight - w(190),
                w(150),
                halfHeight - w(10),
                whitePaint
            )
            canvas.drawRect(
                w(210),
                halfHeight - w(190),
                w(270),
                halfHeight - w(10),
                whitePaint
            )

            //draw resume icon
            canvas.drawPath(resume, whitePaint)
            whitePaint.textSize = w(40)
            whitePaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume", w(132), halfHeight + w(39), whitePaint)
        }
    }


    class GameOver(context: Context) {
        val deathMsgPaint = Paint()

        var alpha = 0f
        var rotation = 0f
        var maxRotation = 0f
        var scale = 0f

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
            deathMsgImg = BitmapFactory.decodeResource(context.resources, R.drawable.death_msg, opts)
        }


        fun setup() {
            alpha = 0f
            rotation = 0f
            maxRotation = -20f + Random.nextFloat() * 40f
            scale = 0f
        }


        fun draw() {
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, dimmerPaint)


            alpha += 2040f / fps
            rotation += (maxRotation * 8f) / fps
            scale += 8f / fps

            if (alpha > 255f) alpha = 255f
            if (rotation > maxRotation) rotation = maxRotation
            if (scale > 1f) scale = 1f

            deathMsgPaint.alpha = alpha.toInt()

            canvas.save()
            canvas.rotate(rotation, halfWidth, h(80))
            canvas.drawBitmap(deathMsgImg, null, deathMsgDim.scale(scale), deathMsgPaint)
            canvas.restore()
        }
    }
}
