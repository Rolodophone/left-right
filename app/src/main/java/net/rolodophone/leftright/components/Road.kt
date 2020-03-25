package net.rolodophone.leftright.components

import android.graphics.Bitmap
import android.graphics.RectF
import android.os.SystemClock
import android.support.annotation.CallSuper
import net.rolodophone.leftright.main.*
import net.rolodophone.leftright.main.MainView.c
import net.rolodophone.leftright.resources.bitmaps
import net.rolodophone.leftright.resources.sounds

class Road {
    var isFrenzy = false

    val background = Background()

    inner class Background {
        private val lineW = w(3)
        private val lineH = w(100)
        private val lineGap = w(40)

        var topLineBottom = 0f

        fun update() {
            topLineBottom += c.player.ySpeed / fps
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


    abstract inner class Item {

        var disabled = false

        open val rotation = 0f

        abstract val isObstacle: Boolean
        abstract var lane: Int
        abstract var img: Bitmap
        abstract val dim: RectF

        fun rectFFromDim(width: Float, height: Float): RectF {
            return RectF(
                centerOfLane(lane) - width / 2,
                -height,
                centerOfLane(lane) + width / 2,
                0f
            )
        }

        fun randomLane(): Int {
            val remainingLanes = mutableSetOf<Int>()
            remainingLanes.addAll(0 until c.road.numLanes)

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
            dim.offset(0f, c.player.ySpeed / fps)

            //mark offscreen item for deletion
            if (dim.top > height) itemsToDel.add(this)

            //perform onTouch()
            if (dim.bottom > c.player.dim.top && dim.top < c.player.dim.bottom && dim.right > c.player.dim.left && dim.left < c.player.dim.right && !disabled) onTouch()
        }

        open fun draw() {
            canvas.save()
            canvas.rotate(rotation, dim.centerX(), dim.centerY())
            canvas.drawBitmap(img, null, dim, bitmapPaint)
            canvas.restore()
        }
    }

    inner class Fuel : Item() {
        override var img = bitmaps.fuel
        override var lane = randomLane()
        override val isObstacle = false
        override val dim = rectFFromDim(w(45), w(51.4285714286f))

        override fun onTouch() {
            c.player.fuel += 50f
            itemsToDel.add(this)
            sounds.playFuel()
        }
    }

    inner class Cone : Item() {
        override var img = bitmaps.cone
        override var lane = randomLane()
        override val isObstacle = true
        override val dim = rectFFromDim(w(45), w(51.4285714286f))

        override fun onTouch() {
            c.player.die(DeathType.CONE, this)
        }
    }

    inner class Oil : Item() {
        override var img = bitmaps.oil
        override var lane = randomLane()
        override val isObstacle = false
        override val dim = rectFFromDim(w(135), w(135))

        override fun onTouch() {
            c.player.oil()
            this.disabled = true
            sounds.playOil()
        }
    }

    inner class Car1 : Item() {
        override var img = bitmaps.car1
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(30) / fps)
        }
    }

    inner class Car2 : Item() {
        override var img = bitmaps.car2
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(50) / fps)
        }
    }

    inner class Car3 : Item() {
        override var img = bitmaps.car3
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(100) / fps)
        }
    }

    inner class Car4 : Item() {
        override var img = bitmaps.car4
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(140) / fps)
        }
    }

    inner class Car5 : Item() {
        override var img = bitmaps.car5
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(170) / fps)
        }
    }

    inner class Car6 : Item() {
        override var img = bitmaps.car6
        override var lane = randomLane()
        override val isObstacle = true
        override val rotation = 180f
        override val dim = rectFFromDim(w(87.5f), w(175))

        override fun onTouch() {
            c.player.die(DeathType.CAR, this)
        }

        override fun update() {
            super.update()

            dim.offset(0f, w(210) / fps)
        }
    }

    inner class Coin : Item() {
        override var img = bitmaps.coin
        override var lane = randomLane()
        override val isObstacle = false
        override val dim = rectFFromDim(w(45), w(45))

        init {
            dim.offset(-w(5), 0f)
        }

        var animationStage = 0f
        var timeSpriteLastChanged = 0L
        var shineNum = -2

        override fun onTouch() {
            c.player.coins += 1
            sounds.playCoin()
            itemsToDel.add(this)
        }

        override fun update() {
            super.update()

            if (animationStage % 2 <= 1) dim.offset(w(20) / fps, 0f) else dim.offset(
                w(
                    -20
                ) / fps, 0f)

            if (SystemClock.elapsedRealtime() - timeSpriteLastChanged > 70) {
                if (shineNum <= 6) {
                    shineNum += 1
                    if (shineNum >= 0) img = bitmaps.coinShining[shineNum]
                    timeSpriteLastChanged = SystemClock.elapsedRealtime()
                } else img = bitmaps.coin
            }

            animationStage += 2f / fps
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
                if (randomChance(60)) items.add(Cone())
                if (randomChance(80)) items.add(Oil())
                if (randomChance(15)) items.add(Car1())
                if (randomChance(20)) items.add(Car2())
                if (randomChance(25)) items.add(Car3())
                if (randomChance(30)) items.add(Car4())
                if (randomChance(35)) items.add(Car5())
                if (randomChance(40)) items.add(Car6())
                if (randomChance(3)) items.add(Coin())
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
                if (randomChance(2)) items.add(Coin())
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