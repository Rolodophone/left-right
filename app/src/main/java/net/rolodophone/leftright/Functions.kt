package net.rolodophone.leftright

fun centerOfLane(lane: Int): Float {
    if (lane >= Road.NUM_LANES) throw IllegalArgumentException("lane index must be less than Road.NUM_LANES")

    return (width * (lane + 1) - halfWidth) / (Road.NUM_LANES)
}


fun gameOver() {
    state = PAUSED
}

operator fun Int.not() = unit * this
operator fun Float.not() = unit * this