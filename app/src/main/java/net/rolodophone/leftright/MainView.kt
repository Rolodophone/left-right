package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView


const val MAIN: Byte = 0
const val GAME: Byte = 1
const val PAUSED: Byte = 2

var state = GAME
var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()
var paint = Paint()

lateinit var player: Player
lateinit var road: Road
lateinit var gui: Gui


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

                    if (state == GAME) {
                        road.draw()
                        player.draw()
                        gui.gameGui.draw()
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

        paint.color = Color.rgb(255, 255, 255)

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
            if (state == PAUSED) {
                state = GAME
                return true
            }


            //handle pausing
            if (state == GAME && event.x < gui.gameGui.pauseW + 2 * Gui.padding && event.y > height - gui.gameGui.pauseH - 2 * Gui.padding) {
                state = PAUSED
                return true
            }


            //handle turning left and right
            if (event.x < width / 2f) {

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