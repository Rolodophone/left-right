package net.rolodophone.leftright

class Road {
    companion object {
        val numLanes = 3

        val lineW = w(3)
        val lineH = w(100)
        val lineGap = w(40)

        var topLineBottom = 0f
    }

    var fuels = ItemType(
        bitmaps.fuel,
        w(45),
        w(45) * 8f / 7f,
        10
    ) {
        for (item in it.list) if (item.dim.bottom > player.dim.top && item.dim.top < player.dim.bottom && item.dim.right > player.dim.left && item.dim.left < player.dim.right) {
            player.fuel += 50f
            it.toDel.add(item)
        }
    }

    var cones = ItemType(
        bitmaps.cone,
        w(45),
        w(45),
        10
    ) {
        for (item in it.list) if (item.dim.bottom >= player.dim.top && item.dim.top < player.dim.bottom && item.dim.right > player.dim.left && item.dim.left < player.dim.right) {
            player.die(DeathType.CONE, item)
        }
    }

    val items = mutableListOf(fuels, cones)


    fun update() {
        topLineBottom += player.ySpeed / fps
        topLineBottom %= lineH + lineGap

        for (item in items) item.update()
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

    fun drawItems() {
        for (item in items) item.draw()
    }
}