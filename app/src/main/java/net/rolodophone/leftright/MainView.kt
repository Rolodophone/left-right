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
    lateinit var cars: GameObjectArray<Car>
    lateinit var fuels: GameObjectArray<Fuel>

    lateinit var gameObjects: List<GameObject>


    override fun run() {
        setup()

        while (appOpen) {
            val initialTime = System.currentTimeMillis()

            for (obj in gameObjects) {
                obj.update()
            }

            if (holder.surface.isValid) {
                canvas = holder.lockCanvas()

                for (obj in gameObjects) {
                    obj.draw()
                }

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
        cars = GameObjectArray()
        fuels = GameObjectArray()

        gameObjects = listOf(player, road, gui, cars, fuels)
    }


    fun pause() {
        paused = true
    }


    // Called once at the same time as run()
    fun resume() {
        paused = false
    }

}