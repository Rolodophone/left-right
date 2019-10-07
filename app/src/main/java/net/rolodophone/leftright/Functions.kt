package net.rolodophone.leftright

fun centerOfLane(lane: Int): Float {
    if (lane >= Road.NUM_LANES) throw IllegalArgumentException("lane index must be less than Road.NUM_LANES")

    return (width * (lane + 1) - halfWidth) / (Road.NUM_LANES)
}


fun w(n: Int): Float = wUnit * n
fun w(n: Float): Float = wUnit * n
fun h(n: Int): Float = hUnit * n
fun h(n: Float): Float = hUnit * n