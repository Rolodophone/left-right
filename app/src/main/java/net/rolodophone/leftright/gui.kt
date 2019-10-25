package net.rolodophone.leftright

import android.graphics.*
import android.os.SystemClock
import kotlin.random.Random

object gui {
    val buttons = listOf(debug.spawnCone, debug.spawnFuel, debug.killPlayer, game.pause, paused.resume, gameOver.playAgain, gameOver.mainMenu, game.leftButton, game.rightButton)

    object game {
        val pause = Button(RectF(w(5), height - w(50), w(50), height - w(5)), { state == stateGame }, {

            canvas.drawRect(
                w(5),
                height - w(50),
                w(20),
                height - w(5),
                whitePaint
            )
            canvas.drawRect(
                w(35),
                height - w(50),
                w(50),
                height - w(5),
                whitePaint
            )

        }) {
            state = statePaused
        }

        val leftButton = Button(RectF(0f, 0f, halfWidth - 1f, height), { state == stateGame }, {}) {

            //if the player turns in between lanes, set the lane to the lane it would have gone to
            if (player.goingR) {
                player.lane++
            }

            if (player.lane != 0) player.goingL = true
            player.goingR = false
        }

        val rightButton = Button(RectF(halfWidth, 0f, width, height), { state == stateGame }, {}) {

            //if the player turns in between lanes, set the lane to the lane it would have gone to
            if (player.goingL) {
                player.lane--
            }

            if (player.lane != road.numLanes - 1) player.goingR = true
            player.goingL = false
        }


        fun draw() {
            pause.draw()
        }
    }


    object debug {
        val gridPaint = Paint()

        var prevTime = SystemClock.elapsedRealtime()
        var viewFps = fps.toInt()

        val killPlayer = ButtonText("kill player", Paint.Align.RIGHT, RectF(w(200), statusBarHeight + w(30), w(353), statusBarHeight + w(55)), { isDebug && state == stateGame }) {
            player.die(DeathType.NONE, null)
        }
        val spawnCone = ButtonText("spawn cone", Paint.Align.RIGHT, RectF(w(200), statusBarHeight + w(60), w(353), statusBarHeight + w(85)), { isDebug && state == stateGame }) {
            road.items.add(road.Cone())
        }
        val spawnFuel = ButtonText("spawn fuel", Paint.Align.RIGHT, RectF(w(200), statusBarHeight + w(90), w(353), statusBarHeight + w(115)), { isDebug && state == stateGame }) {
            road.items.add(road.Fuel())
        }

        init {
            gridPaint.color = Color.argb(100, 255, 255, 255)
        }

        fun draw() {
            //draw fps

            if (SystemClock.elapsedRealtime() > prevTime + 500) {
                viewFps = fps.toInt()
                prevTime = SystemClock.elapsedRealtime()
            }
            whitePaint.textAlign = Paint.Align.LEFT
            whitePaint.textSize = w(22)
            canvas.drawText("FPS: $viewFps", w(5), w(55) + statusBarHeight, whitePaint)


            //draw grid
            var x = w(20)
            while (x < width) {
                canvas.drawLine(x, 0f, x, height, gridPaint)
                x += w(20)
            }

            x = w(120)
            while (x < width) {
                canvas.drawLine(x, 0f, x, height, whitePaint)
                x += w(120)
            }

            var y = h(20)
            while (y < height) {
                canvas.drawLine(0f, y, width, y, gridPaint)
                y += h(20)
            }

            y = h(120)
            while (y < height) {
                canvas.drawLine(0f, y, width, y, whitePaint)
                y += h(120)
            }

            //draw buttons
            if (state == stateGame) {
                killPlayer.draw()
                spawnCone.draw()
                spawnFuel.draw()
            }
        }
    }


    object status {
        val fuelDim = RectF(
            w(333),
            w(4) + statusBarHeight,
            w(355),
            w(29.1428571429f) + statusBarHeight
        )


        fun draw() {
            whitePaint.textSize = w(22)


            //draw fuel meter
            canvas.drawBitmap(bitmaps.fuel, null, fuelDim, whitePaint)

            whitePaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(
                player.fuel.toInt().toString(),
                w(329),
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
        var resume = Button(RectF(w(90), halfHeight + w(5), w(270), halfHeight + w(49)), { state == statePaused }, {
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


    object gameOver {
        var deathMsgImg = bitmaps.death_msg
        val deathMsgDim = RectF(
            w(30),
            h(40),
            w(330),
            h(40) + w(72.972972973f)
        )
        val deathMsgPaint = Paint()

        val playAgain = ButtonBitmap(bitmaps.play_again, RectF(w(220), h(250), w(300), h(250) + w(80)), { state == stateGameOver }) {
            state = stateGame
        }
        val mainMenu = ButtonBitmap(bitmaps.main_menu, RectF(w(60), h(250), w(140), h(250) + w(80)), { state == stateGameOver }) {
            //state = stateMain
        }

        val comments = mapOf(
            DeathType.NONE to listOf(
                listOf("Better luck next time!"),
                listOf("Oops, I forgot my seat belt..."),
                listOf("Death can be fatal..."),

                listOf("Magic!")
            ),

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
            ),

            DeathType.CAR to listOf(
                listOf("Better luck next time!"),
                listOf("Oops, I forgot my seat belt..."),
                listOf("Death can be fatal...")
            )
        )


        private var alpha = 0f
        private var rotation = 0f
        private var maxRotation = 0f
        private var scale = 0f

        lateinit var comment: List<String>


        fun reset() {
            alpha = 0f
            rotation = 0f
            maxRotation = -20f + Random.nextFloat() * 40f
            scale = 0f

            comment = comments.getValue(player.causeOfDeath).random()
        }


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
