package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF
import android.support.annotation.CallSuper

object road {

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

        val lane = (0 until numLanes).random()
        var disabled = false

        open val rotation = 0f

        abstract var img: Bitmap
        abstract val dim: RectF

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
            if (randomChance(256)) items.add(Fuel())
            if (randomChance(320)) items.add(Cone())
            if (randomChance(640)) items.add(Oil())
            if (randomChance(1280)) items.add(Car1())
            if (randomChance(1920)) items.add(Car2())
            if (randomChance(2560)) items.add(Car3())
            if (randomChance(3280)) items.add(Car4())
            if (randomChance(4160)) items.add(Car5())
            if (randomChance(5600)) items.add(Car6())
        }

        for (item in items) item.update()
        for (item in itemsToDel) items.remove(item)
    }


    fun draw() {
        background.draw()
        for (item in items) item.draw()
    }
}