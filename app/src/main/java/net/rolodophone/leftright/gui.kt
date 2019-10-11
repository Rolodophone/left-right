package net.rolodophone.leftright

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.SystemClock
import kotlin.random.Random

object gui {
    val padding = w(5)

    var gameOver = GameOver()

    object game {
        val pause = Button(RectF(padding, height - padding - w(45), padding + w(45), height - padding), stateGame, {

            canvas.drawRect(
                padding,
                height - padding - w(45),
                padding + w(15),
                height - padding,
                whitePaint
            )
            canvas.drawRect(
                padding + w(30),
                height - padding - w(45),
                padding + w(45),
                height - padding,
                whitePaint
            )

        }) {
            state = statePaused
        }

        val leftButton = Button(RectF(0f, 0f, halfWidth - 1f, height), stateGame, {}) {

            //if the player turns in between lanes, set the lane to the lane it would have gone to
            if (player.goingR) {
                player.lane++
            }

            if (player.lane != 0) player.goingL = true
            player.goingR = false
        }

        val rightButton = Button(RectF(halfWidth, 0f, width, height), stateGame, {}) {

            //if the player turns in between lanes, set the lane to the lane it would have gone to
            if (player.goingL) {
                player.lane--
            }

            if (player.lane != Road.numLanes - 1) player.goingR = true
            player.goingL = false
        }

        init {
            buttons.add(pause)
            buttons.add(leftButton)
            buttons.add(rightButton)
        }


        fun draw() {
            pause.draw()
        }
    }


    object debug {
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
                whitePaint.textSize = w(22)
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


    object status {
        var fuelImg = bitmaps.getValue("fuel")
        val fuelW = w(22)
        val fuelH = fuelW * (16f / 14f)
        val fuelDim = RectF(
            width - fuelW - padding,
            padding + statusBarHeight,
            width - padding,
            padding + fuelH + statusBarHeight
        )


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


    object paused {
        var resume = Button(RectF(w(90), halfHeight + w(5), w(270), halfHeight + w(49)), statePaused, {
            val path = Path()
            path.moveTo(w(87), halfHeight + w(5))
            path.lineTo(w(87), halfHeight + w(49))
            path.lineTo(w(120), halfHeight + w(27))
            path.close()

            canvas.drawPath(path, whitePaint)
            whitePaint.textSize = w(40)
            whitePaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Resume", w(132), halfHeight + w(39), whitePaint)
        }) {
            state = stateGame
        }

        init {
            buttons.add(resume)
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

            resume.draw()
        }
    }


    class GameOver {
        companion object {
            var deathMsgImg = bitmaps.getValue("death_msg")
            val deathMsgDim = RectF(
                w(30),
                h(80),
                w(330),
                h(80) + w(72.972972973f)
            )
        }



        val deathMsgPaint = Paint()

        var alpha = 0f
        var rotation = 0f
        var maxRotation = -20f + Random.nextFloat() * 40f
        var scale = 0f


        fun update() {
            //update wasted image
            alpha += 2040f / fps
            rotation += (maxRotation * 8f) / fps
            scale += 8f / fps

            if (alpha > 255f) alpha = 255f
            if (maxRotation > 0f && rotation > maxRotation || maxRotation < 0f && rotation < maxRotation) rotation = maxRotation
            if (scale > 1f) scale = 1f

            deathMsgPaint.alpha = alpha.toInt()
        }


        fun draw() {
            //dim rest of screen
            canvas.drawRect(0f, 0f, width, height, dimmerPaint)


            //draw wasted image
            canvas.save()
            canvas.rotate(rotation, halfWidth, h(80))
            canvas.drawBitmap(deathMsgImg, null, deathMsgDim.scale(scale), deathMsgPaint)
            canvas.restore()
        }
    }
}
