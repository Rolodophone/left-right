package net.rolodophone.leftright.main

import android.annotation.SuppressLint
import android.os.SystemClock
import android.util.Log
import android.view.SurfaceView
import net.rolodophone.leftright.stategame.StateGame


@SuppressLint("ViewConstructor")
class MainView(private val ctx: MainActivity) : SurfaceView(ctx), Runnable {

    override fun run() {
        while (appOpen) {
            val initialTime = SystemClock.elapsedRealtime()

            if (holder.surface.isValid) {

                //update
                ctx.state.update()

                //draw
                val c = holder.lockCanvas()
                if (c != null) {
                    canvas = c

                    ctx.state.draw()
                    //ctx.grid.draw()

                    holder.unlockCanvasAndPost(canvas)
                }

                //calculate fps
                val timeElapsed = SystemClock.elapsedRealtime() - initialTime
                fps = if (timeElapsed == 0L) 2000f else 1000f / timeElapsed
                if (isDebug && fps < 30f) fps = 30f
            }


            else {
                Log.e("View", "Surface invalid")
            }
        }
    }


    fun pause() {
        ctx.state.let {
            if (it is StateGame && it.state == StateGame.State.UNPAUSED) {
                it.pauseGame()
            }
        }
    }


    fun resume() {}
}