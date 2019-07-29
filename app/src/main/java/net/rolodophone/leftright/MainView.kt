package net.rolodophone.leftright

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.SurfaceView


var paused = false
var fps = 0f
var canvas = Canvas()
var paint = Paint()

@SuppressLint("ViewConstructor")
class MainView(activity: Context) : SurfaceView(activity), Runnable {

    lateinit var player: Player
    lateinit var road: Road
    lateinit var gui: Gui
    lateinit var cars: MutableList<Car>
    lateinit var fuels: MutableList<Fuel>
    lateinit var gameLogic: GameLogic


    override fun run() {
        setup()

        while (appOpen) {
            val initialTime = System.currentTimeMillis()

            road.update()
            player.update()
            gui.update()
            for (car in cars) car.update()
            for (fuel in fuels) fuel.update()
            gameLogic.update()


            if (holder.surface.isValid) {
                canvas = holder.lockCanvas()

                road.draw()
                for (car in cars) car.draw()
                for (fuel in fuels) fuel.draw()
                player.draw()
                gui.draw()

                holder.unlockCanvasAndPost(canvas)
            }

            else {
                Log.e("View", "Surface invalid")
            }


            val timeElapsed = System.currentTimeMillis() - initialTime

            fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
        }
    }


    fun setup() {
        player = Player()
        road = Road()
        gui = Gui()
        cars = mutableListOf()
        fuels = mutableListOf()
        gameLogic = GameLogic()
    }


    fun pause() {
        paused = true
    }


    // Called once at the same time as run()
    fun resume() {
        paused = false
    }

}