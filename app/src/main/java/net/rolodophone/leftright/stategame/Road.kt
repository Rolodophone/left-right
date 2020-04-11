package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.*

class Road(override val state: StateGame) : Component {
    var isFrenzy = false
    val numLanes = 3
    val objects = mutableListOf<Object>()
    val itemsToDel = mutableListOf<Object>()

    private val background = Background(state)

    override fun update() {
        background.update()

        //spawn new objects
        if (fps != Float.POSITIVE_INFINITY) {
            if (!isFrenzy) {
                for (itemType in listOf(Fuel, Cone, Oil, Coin, Car1, Car2, Car3, Car4, Car5, Car6)) if (randomChance(itemType.averageSpawnMetres.toFloat())) itemType.spawn(state)
            }
            else {
                for (itemType in listOf(Fuel, Cone, Oil, Coin, Car1, Car2, Car3, Car4, Car5, Car6)) {
                    if (randomChance(itemType.averageSpawnMetres.toFloat() / 5)) itemType.spawn(state)
                }
            }
        }

        //sort by the z value so they get drawn and updated in the correct order
        objects.sortBy {it.z}

        for (item in objects) item.update()
        for (item in itemsToDel) objects.remove(item)
        itemsToDel.clear()
    }

    override fun draw() {
        background.draw()
        for (item in objects) item.draw()
    }

    fun centerOfLane(lane: Int): Float {
        require(lane < numLanes) { "lane index must be less than road.numLanes" }

        return (width * (lane + 1) - halfWidth) / numLanes
    }

    private fun randomChance(averageMetres: Float): Boolean {
        return (0 until ((averageMetres / state.player.ySpeedMps) * fps).toInt()).random() == 0
    }


    fun randomLane(): Int {
        val remainingLanes = mutableSetOf<Int>()
        remainingLanes.addAll((0 until numLanes).filter { checkValidLane(it) })

        return if (remainingLanes.isEmpty()) -1
        else remainingLanes.random()
    }


    fun checkValidLane(laneToCheck: Int): Boolean {

        //too close to another item
        for (item in objects) if (item.lane == laneToCheck && item.dim.top < w(5)) {
            return false
        }

        //making it impossible for player to pass (an obstacle in each lane)

        val remainingLanes = mutableSetOf<Int>()
        remainingLanes.addAll(0 until numLanes)
        remainingLanes.remove(laneToCheck)

        for (item in objects) {
            if (item is Obstacle) remainingLanes.remove(item.lane)
        }

        if (remainingLanes.isEmpty()) return false

        return true
    }
}