package net.rolodophone.leftright.stateareas

import android.graphics.RectF
import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.stategame.StateGame

class StateAreas(ctx: MainActivity) : State(ctx) {
    override val numThingsToLoad = 1

    private var area = 0
    private var areaIsReady = false
    private var areaIsAwaitingMusic = false
    private var seekDistance = 0f
    private var seekStartX: Float? = null

    private val areas = listOf(StateGame(ctx))

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

        if (seekDistance != 0f) {
            canvas.save()
            canvas.translate(seekDistance, 0f)
            for (area in areas) area.road.draw()
            for (area in areas) area.weather.draw()
            canvas.restore()
        }
        else {
            areas[area].road.draw()
            areas[area].weather.draw()
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
        seekDistance = 0f
    }

    fun seek(toX: Float) {
        seekDistance = seekStartX.let {
            if (it != null) toX - it
            else 0f
        }
    }
}