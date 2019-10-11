package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager
import net.rolodophone.leftright.Gui.Companion.padding


const val MAIN: Byte = 0
const val GAME: Byte = 1
const val PAUSED: Byte = 2
const val GAMEOVER: Byte = 3

var state = GAME
var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()

var whitePaint = Paint()
var dimmerPaint = Paint()

lateinit var player: Player
lateinit var road: Road
lateinit var gui: Gui

const val isDebug = true


class MainView(context: Context) : SurfaceView(context), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {

                //update
                if (state == GAME) {
                    road.update()
                    player.update()
                }

                //draw
                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    when (state) {

                        MAIN -> {

                        }

                        GAME -> {
                            road.draw()
                            player.draw()
                            gui.status.draw()
                            gui.game.draw()
                            if (isDebug) gui.debug.draw()
                        }

                        PAUSED -> {
                            road.draw()
                            player.draw()
                            gui.status.draw()
                            gui.paused.draw()
                            if (isDebug) gui.debug.draw()
                        }

                        GAMEOVER -> {
                            road.draw()
                            player.draw()
                            gui.gameOver.draw()
                            if (isDebug) gui.debug.draw()
                        }
                    }

                    holder.unlockCanvasAndPost(canvas)
                }


                val timeElapsed = SystemClock.elapsedRealtime() - initialTime
                fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
            }


            else {
                Log.e("View", "Surface invalid")
            }
        }
    }


    fun setup() {
        Log.i("View", "<---------SETUP--------->")

        holder.setFormat(PixelFormat.RGB_565)

        whitePaint.color = Color.rgb(255, 255, 255)
        whitePaint.isAntiAlias = true
        dimmerPaint.color = Color.argb(100, 0, 0, 0)


        val activity = context as Activity
        val window = activity.window

        window.statusBarColor = Color.argb(0, 0, 0, 0)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)


        //player initialised first because measurements depend on player's width
        player = Player(context)
        road = Road(context)

        gui = Gui(context)

        Log.i("View", "</--------SETUP--------->")
    }


    @SuppressLint("ClickableViewAccessibility")

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {

            //handle unpausing
            if (state == PAUSED && event.x > w(90) && event.x < w(270) && event.y > halfHeight + w(5) && event.y < halfHeight + w(49)) {
                state = GAME
            }


            //handle pausing
            else if (state == GAME && event.x < gui.game.pauseW + 2 * padding && event.y > height - gui.game.pauseH - 2 * padding) {
                state = PAUSED
            }


            //handle turning left and right
            else if (state == GAME && event.x < halfWidth) {

                //if the player turns in between lanes, set the lane to the lane it would have gone to
                if (player.goingR) {
                    player.lane++
                }

                if (player.lane != 0) player.goingL = true
                player.goingR = false
            } else {
                //if the player turns in between lanes, set the lane to the lane it would have gone to
                if (player.goingL) {
                    player.lane--
                }

                if (player.lane != Road.NUM_LANES - 1) player.goingR = true
                player.goingL = false
            }


            return true

        } else return false
    }


    fun pause() {
        if (state == GAME) state = PAUSED
        Log.i("View", "Paused")
    }


    // Called once at the same time as run()
    fun resume() {
        if (state == PAUSED) state = GAME
        Log.i("View", "Resumed")
    }

}