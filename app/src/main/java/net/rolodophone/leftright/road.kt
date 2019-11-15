package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF
import android.support.annotation.CallSuper

object road {

    var isFrenzy = false

    object background {
        private val lineW = w(3)
        private val lineH = w(100)
        private val lineGap = w(40)

        var topLineBottom = 0f

        fun update() {
            topLineBottom += player.ySpeed / fps
            topLineBottom %= lineH + lineGap
        }

        fun draw() {
            //draw background
            canvas.drawRGB(111, 111, 111)

            //draw lines
            var y = topLineBottom
            while (y <= height + lineH) {

                for (i in 1 until numLanes) {
                    canvas.drawRect(
                        ((width * i) / numLanes) - (lineW / 2),
                        y - lineH,
                        ((width * i) / numLanes) + (lineW / 2),
                        y,
                        whitePaint
                    )
                }

                y += lineH + lineGap
            }
        }
    }


    abstract class Item {

        var disabled = false

        open val rotation = 0f

        abstract val isObstacle: Boolean

        abstract var lane: Int
        abstract var img: Bitmap
        abstract val dim: RectF

        fun randomLane(): Int {
            val remainingLanes = mutableSetOf<Int>()
            remainingLanes.addAll(0 until numLanes)

            var newLane: Int
            while (true) {
                if (remainingLanes.isEmpty()) {
                    itemsToDel.add(this)
                    return 0
                }

                newLane = remainingLanes.random()
                remainingLanes.remove(newLane)

                if (checkValidLane(newLane)) {
                    return newLane
                }
            }
        }

        private fun checkValidLane(laneToCheck: Int): Boolean {

            //touching another item
            for (item in items) if (item.lane == laneToCheck && item.dim.top < w(5)) {
                return false
            }

            //making it impossible for player to pass (an obstacle in each lane)

            val remainingLanes = mutableSetOf<Int>()
            remainingLanes.addAll(0 until numLanes)
            remainingLanes.remove(laneToCheck)

            for (item in items) {
                if (item.isObstacle) remainingLanes.remove(item.lane)
            }

            if (remainingLanes.isEmpty()) return false

            return true
        }

        abstract fun onTouch()

        @CallSuper
        open fun update() {
            //move item down
            dim.offset(0f, player.ySpeed / fps)

            //mark offscreen item for deletion
            if (dim.top > height) itemsToDel.add(this)

            //perform onTouch()
            if (dim.bottom > player.dim.top && dim.top < player.dim.bottom && dim.right > player.dim.left && dim.left < player.dim.right && !disabled) onTouch()
        }

        open fun draw() {
            canvas.save()
            canvas.rotate(rotation, dim.centerX(), dim.centerY())
            canvas.drawBitmap(img, null, dim, whitePaint)
            canvas.restore()
        }
    }

    class Fuel : Item() {
        override var img = bitmaps.fuel
        override var lane = randomLane()
        override val isObstacle = false

        override val dim = RectF(
            centerOfLane(lane) - w(22.5f),
            -w(51.4285714286f),
            centerOfLane(lane) + w(22.5f),
            0f
        )

        override fun onTouch() {
            player.fuel += 50f
            itemsToDel.add(this)
            sounds.playFuel()
        }
    }

    class Cone : Item() {
        override var img = bitmaps.cone
        override var lane = randomLane()
        override val isObstacle = true

        override val dim = RectF(
            centerOfLane(lane) - w(22.5f),
            -w(51.4285714286f),
            centerOfLane(lane) + w(22.5f),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CONE, this)
        }
    }

    class Oil : Item() {
        override var img = bitmaps.oil
        override var lane = randomLane()
        override val isObstacle = false

        override val dim = RectF(
            centerOfLane(lane) - w(67.5f),
            -w(135),
            centerOfLane(lane) + w(67.5f),
            0f
        )

        override fun onTouch() {
            player.oil()
            this.disabled = true
            sounds.playOil()
        }
    }

    class Car1 : Item() {
        override var img = bitmaps.car1.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(30) / fps)
        }
    }

    class Car2 : Item() {
        override var img = bitmaps.car2.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(50) / fps)
        }
    }

    class Car3 : Item() {
        override var img = bitmaps.car3.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(100) / fps)
        }
    }

    class Car4 : Item() {
        override var img = bitmaps.car4.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(140) / fps)
        }
    }

    class Car5 : Item() {
        override var img = bitmaps.car5.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(170) / fps)
        }
    }

    class Car6 : Item() {
        override var img = bitmaps.car6.clean
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f

        override val dim = RectF(
            centerOfLane(lane) - w(45),
            -w(180),
            centerOfLane(lane) + w(45),
            0f
        )

        override fun onTouch() {
            player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(210) / fps)
        }
    }


    val numLanes = 3

    lateinit var items: MutableList<Item>
    lateinit var itemsToDel: MutableList<Item>


    fun reset() {
        items = mutableListOf()
        itemsToDel = mutableListOf()
    }


    fun update() {
        background.update()

        //spawn new items
        if (fps != Float.POSITIVE_INFINITY) {
            if (!isFrenzy) {
                if (randomChance(15)) items.add(Fuel())
                if (randomChance(15)) items.add(Cone())
                if (randomChance(25)) items.add(Oil())
                if (randomChance(50)) items.add(Car1())
                if (randomChance(75)) items.add(Car2())
                if (randomChance(100)) items.add(Car3())
                if (randomChance(125)) items.add(Car4())
                if (randomChance(150)) items.add(Car5())
                if (randomChance(200)) items.add(Car6())
            } else {
                if (randomChance(3)) items.add(Fuel())
                if (randomChance(6)) items.add(Cone())
                if (randomChance(10)) items.add(Oil())
                if (randomChance(20)) items.add(Car1())
                if (randomChance(30)) items.add(Car2())
                if (randomChance(40)) items.add(Car3())
                if (randomChance(50)) items.add(Car4())
                if (randomChance(60)) items.add(Car5())
                if (randomChance(80)) items.add(Car6())
            }
        }

        for (item in items) item.update()
        for (item in itemsToDel) items.remove(item)
    }


    fun draw() {
        background.draw()
        for (item in items) item.draw()
    }
}