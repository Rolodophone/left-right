package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView


var paused = false
var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()
var paint = Paint()

lateinit var player: Player
lateinit var road: Road
lateinit var gui: Gui
lateinit var cars: MutableList<Car>
lateinit var fuels: MutableList<Fuel>


class MainView(context: Context) : SurfaceView(context), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {

                if (!paused) {
                    road.update()
                    player.update()
                    gui.update()
                    for (car in cars) car.update()

                    for (fuel in fuels) fuel.update()
                    for (fuel in Fuel.fuelsToDel) fuels.remove(fuel)
                    Fuel.fuelsToDel.clear()


                    //spawn fuels
                    if (fps != Float.POSITIVE_INFINITY && (0 until (Fuel.spawnRate * fps.toInt())).random() == 0) {
                        fuels.add(Fuel((0 until road.numLanes).random()))
                        Log.i("View", "Fuel spawned in lane ${fuels.last().lane}")
                    }
                }


                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    //set background to black (for debugging)
                    //canvas.drawColor(Color.BLACK)

                    road.draw()
                    for (car in cars) car.draw()
                    for (fuel in fuels) fuel.draw()
                    player.draw()
                    gui.draw()

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

        road = Road(context)
        player = Player(context)
        gui = Gui(context)
        cars = mutableListOf()
        Fuel.init(context)
        fuels = mutableListOf()

        Log.i("View", "</--------SETUP--------->")
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {

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

                if (player.lane != road.numLanes - 1) player.goingR = true
                player.goingL = false
            }

            return true
        } else return false
    }


    fun pause() {
        paused = true
        Log.i("View", "Paused")
    }


    // Called once at the same time as run()
    fun resume() {
        paused = false
        Log.i("View", "Resumed")
    }

}