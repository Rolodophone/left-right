package net.rolodophone.leftright.stateareas

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.stategame.StateGame

class StateAreas(ctx: MainActivity, private var area: Int = 0) : State(ctx) {
    override val numThingsToLoad = 1

    private var areaIsReady = false
    private var areaIsAwaitingMusic = false
    private var seekDistance = 0f
    private var seekStartX: Float? = null

    private val areas = listOf(StateGame(ctx, 0), StateGame(ctx, 1))

    private val selectAreaButton = Button(this, RectF(0f, 0f, width, height)) {
        ctx.sounds.playTap()
        if (areaIsReady) startArea()
        else areaIsAwaitingMusic = true
    }


    override fun update() {
        selectAreaButton.update()
    }

    override fun draw() {
        selectAreaButton.draw()

        area.let {
            if (seekDistance != 0f) {
                canvas.save()
                canvas.translate(seekDistance, 0f)

                areas[it].road.draw()
                areas[it].weather.draw()

                //draw adjacent areas
                if (seekDistance < 0f) {
                    canvas.save()
                    canvas.translate(width, 0f)

                    areas[it + 1].road.draw()
                    areas[it + 1].weather.draw()

                    canvas.restore()
                }
                else if (seekDistance > 0f) {
                    canvas.save()
                    canvas.translate(-width, 0f)

                    areas[it - 1].road.draw()
                    areas[it - 1].weather.draw()

                    canvas.restore()
                }

                canvas.restore()
            }
            else {
                areas[it].road.draw()
                areas[it].weather.draw()
            }
        }
    }

    fun onMusicReady() {
        if (areaIsAwaitingMusic) startArea()
        else areaIsReady = true
    }

    fun startArea() {
        ctx.state = areas[area]
        ctx.music.resume()
    }

    fun startSeek(x: Float) {
        seekStartX = x
    }

    fun stopSeek() {
        seekStartX = null
        if (seekDistance > width / 2) area--
        else if (seekDistance < -width / 2) area++
        seekDistance = 0f
    }

    fun seek(toX: Float) {
        seekDistance = seekStartX.let {
            if (it != null) {
                val newSeekDistance = toX - it

                if ((area == 0 && newSeekDistance > 0f) || (area == areas.size - 1 && newSeekDistance < 0)) 0f
                else newSeekDistance
            }
            else 0f
        }
    }

    fun flingLeft() {
        if (area != areas.size - 1) area++
        seekStartX = null
        seekDistance = 0f
    }

    fun flingRight() {
        if (area != 0) area--
        seekStartX = null
        seekDistance = 0f
    }
}