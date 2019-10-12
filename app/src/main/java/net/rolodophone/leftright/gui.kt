package net.rolodophone.leftright

import android.graphics.*
import android.os.SystemClock
import kotlin.random.Random

object gui {
    val padding = w(5)

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
        const val showFps = true
        const val showGrid = true

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
                canvas.drawText("FPS: $viewFps", padding, w(55) + statusBarHeight, whitePaint)
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
        var fuelImg = bitmaps.fuel
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
            var deathMsgImg = bitmaps.death_msg
            val deathMsgDim = RectF(
                w(30),
                h(40),
                w(330),
                h(40) + w(72.972972973f)
            )
            val deathMsgPaint = Paint()

            val playAgain = BitmapButton(bitmaps.play_again, RectF(w(220), h(250), w(300), h(250) + w(80)), stateGameOver) {
                state = stateGame
            }
            val mainMenu = BitmapButton(bitmaps.main_menu, RectF(w(60), h(250), w(140), h(250) + w(80)), stateGameOver) {
                //state = stateMain
            }

            init {
                buttons.add(playAgain)
                buttons.add(mainMenu)
            }

            val comments = mapOf(
                DeathType.CONE to listOf(
                    listOf("Better luck next time!"),
                    listOf("Oops, I forgot my seat belt..."),
                    listOf("Death can be fatal..."),

                    listOf("Who filled this cone", "with concrete?"),
                    listOf("You hit a cone!")
                ),
                DeathType.FUEL to listOf(
                    listOf("Better luck next time!"),
                    listOf("Oops, I forgot my seat belt..."),
                    listOf("Death can be fatal..."),

                    listOf("No one ever told me this", "car explodes when it runs", "out of fuel!"),
                    listOf("You ran out of fuel!"),
                    listOf("Hmm, the accelerator pedal", "doesn't seem to be working")
                )
            )
        }


        var alpha = 0f
        var rotation = 0f
        var maxRotation = -20f + Random.nextFloat() * 40f
        var scale = 0f

        val comment = comments.getValue(player.causeOfDeath).random()


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
            canvas.rotate(rotation, halfWidth, h(60))
            canvas.drawBitmap(deathMsgImg, null, deathMsgDim.scale(scale), deathMsgPaint)
            canvas.restore()

            //draw comment
            whitePaint.textAlign = Paint.Align.CENTER
            whitePaint.typeface = Typeface.DEFAULT_BOLD
            for ((i, line) in comment.withIndex()) canvas.drawText(line, halfWidth, h(120) + w(25 * i), whitePaint)
            whitePaint.typeface = Typeface.DEFAULT

            //draw stats
            whitePaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Distance travelled:", w(30), h(160), whitePaint)
            canvas.drawText("Fuel remaining:", w(30), h(160) + w(30), whitePaint)
            canvas.drawText("Coins collected:", w(30), h(160) + w(60), whitePaint)
            canvas.drawText("Total score:", w(30), h(160) + w(100), whitePaint)

            whitePaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(player.distance.toInt().toString(), w(330), h(160), whitePaint)
            canvas.drawText(player.fuel.toInt().toString(), w(330), h(160) + w(30), whitePaint)
            canvas.drawText(player.coins.toString(), w(330), h(160) + w(60), whitePaint)
            canvas.drawText((player.distance.toInt() + player.fuel.toInt() + player.coins).toString(), w(330), h(160) + w(100), whitePaint)

            whitePaint.strokeWidth = 3f
            canvas.drawLine(w(30), h(160) + w(72), w(330), h(160) + w(72), whitePaint)
            whitePaint.strokeWidth = 1f

            //draw buttons
            playAgain.draw()
            mainMenu.draw()
        }
    }
}
