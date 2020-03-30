package net.rolodophone.leftright.stategame.road

import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.stategame.StateGame

class Road(override val ctx: MainActivity, override val state: StateGame) : Component {
    var isFrenzy = false
    val numLanes = 3
    val items = mutableListOf<Item>()
    val itemsToDel = mutableListOf<Item>()

    private val background = Background(this)

    override fun update() {
        background.update()

        //spawn new items
        if (fps != Float.POSITIVE_INFINITY) {
            if (!isFrenzy) {
                for (itemType in listOf(Fuel, Cone, Oil, Coin, Car1, Car2, Car3, Car4, Car5, Car6)) if (randomChance(itemType.averageSpawnMetres)) itemType.new(this)
            }
            else {
                for (itemType in listOf(Fuel, Cone, Oil, Coin, Car1, Car2, Car3, Car4, Car5, Car6)) if (randomChance(itemType.averageSpawnMetres / 10)) itemType.new(this)
            }
        }

        //sort by the z value so they get drawn and updated in the correct order
        items.sortBy {it.z}

        for (item in items) item.update()
        for (item in itemsToDel) items.remove(item)
    }

    override fun draw() {
        background.draw()
        for (item in items) item.draw()
    }

    fun centerOfLane(lane: Int): Float {
        require(lane < numLanes) { "lane index must be less than road.numLanes" }

        return (width * (lane + 1) - halfWidth) / numLanes
    }

    private fun randomChance(averageMetres: Int): Boolean {
        return (0 until ((averageMetres / state.player.ySpeedMps) * fps).toInt()).random() == 0
    }
}