package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
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
lateinit var gameLogic: GameLogic


@SuppressLint("ViewConstructor")
class MainView(context: Context) : SurfaceView(context), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = System.currentTimeMillis()

            if (holder.surface.isValid) {

                if (!paused) {
                    road.update()
                    player.update()
                    gui.update()
                    for (car in cars) car.update()
                    for (fuel in fuels) fuel.update()
                    gameLogic.update()
                }


                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    //set background to black (for debugging)
                    canvas.drawColor(Color.BLACK)

                    road.draw()
                    for (car in cars) car.draw()
                    for (fuel in fuels) fuel.draw()
                    player.draw()
                    gui.draw()

                    holder.unlockCanvasAndPost(canvas)
                }


                val timeElapsed = System.currentTimeMillis() - initialTime
                Log.v("View", "time elapsed: $timeElapsed")

                fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
                Log.v("View", "FPS: $fps")
            }


            else {
                Log.e("View", "Surface invalid")
            }
        }
    }


    fun setup() {
        Log.i("View", "<---------SETUP--------->")

        player = Player(context)
        road = Road(context)
        gui = Gui(context)
        cars = mutableListOf()
        fuels = mutableListOf()
        gameLogic = GameLogic(context)

        Log.i("View", "</---------SETUP--------->")
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