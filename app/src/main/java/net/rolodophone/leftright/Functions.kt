package net.rolodophone.leftright

import android.graphics.RectF


fun centerOfLane(lane: Int): Float {
    require(lane < Road.numLanes) { "lane index must be less than Road.numLanes" }

    return (width * (lane + 1) - halfWidth) / (Road.numLanes)
}

fun RectF.scale(factor: Float): RectF {
    val diffHorizontal = (right - left) * (factor - 1f)
    val diffVertical = (bottom - top) * (factor - 1f)

    val mTop = top - diffVertical / 2f
    val mBottom = bottom + diffVertical / 2f

    val mLeft = left - diffHorizontal / 2f
    val mRight = right + diffHorizontal / 2f

    return RectF(mLeft, mTop, mRight, mBottom)
}


fun w(n: Int): Float = wUnit * n
fun w(n: Float): Float = wUnit * n
fun h(n: Int): Float = hUnit * n
fun h(n: Float): Float = hUnit * n