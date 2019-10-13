package net.rolodophone.leftright

import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log

class ItemType(val bitmap: Bitmap, val w: Float, val h: Float, val spawnRate: Int, val customUpdate: (ItemType) -> Unit) {

    class Item(val lane: Int, w: Float, h: Float) {

        val dim = RectF(
            centerOfLane(lane) - w / 2f,
            -h,
            centerOfLane(lane) + w / 2f,
            0f
        )

    }


    val list = mutableListOf<Item>()
    val toDel = mutableListOf<Item>()


    fun update() {
        //move items down
        for (item in list) {
            item.dim.offset(0f, player.ySpeed / fps)

            //mark offscreen items for deletion
            if (item.dim.top > height) toDel.add(item)
        }

        //remove items marked for deletion
        for (item in toDel) {
            list.remove(item)
            Log.i("ItemType", "Deleted item $item")
        }
        toDel.clear()


        //spawn new items
        if (fps != Float.POSITIVE_INFINITY && (0 until (spawnRate * fps.toInt())).random() == 0) {
            spawn()
        }

        //perform custom update
        customUpdate(this)
    }


    fun draw() {
        for (item in list) canvas.drawBitmap(bitmap, null, item.dim, whitePaint)
    }

    fun spawn() {
        list.add(Item((0 until road.numLanes).random(), w, h))
    }
}