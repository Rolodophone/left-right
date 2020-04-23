package net.rolodophone.leftright.stateareas

import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State
import net.rolodophone.leftright.main.canvas
import net.rolodophone.leftright.stategame.StateGame

class StateAreas(ctx: MainActivity) : State(ctx) {
    override val numThingsToLoad = 1

    private var area = 0
    private var areaIsReady = false
    private var areaIsAwaitingMusic = false
    private var seekDistance = 0f
    private var seekStartX: Float? = null

    private val areas = listOf(StateGame(ctx))


    override fun update() {}

    override fun draw() {
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
    }

    fun tapArea() {
        ctx.sounds.playTap()
        if (areaIsReady) startArea()
        else areaIsAwaitingMusic = true
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